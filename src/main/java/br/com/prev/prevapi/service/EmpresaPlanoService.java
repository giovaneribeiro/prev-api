package br.com.prev.prevapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prev.prevapi.entity.Empresa;
import br.com.prev.prevapi.entity.EmpresaPlano;
import br.com.prev.prevapi.entity.EmpresaPlanoPk;
import br.com.prev.prevapi.entity.Plano;
import br.com.prev.prevapi.enums.BooleanEnum;
import br.com.prev.prevapi.model.EmpresaPlanoDTO;
import br.com.prev.prevapi.repository.EmpresaPlanoRepository;
import br.com.prev.prevapi.repository.EmpresaRepository;
import br.com.prev.prevapi.repository.PlanoRepository;

@Service
public class EmpresaPlanoService {

    @Autowired
    private EmpresaPlanoRepository empresaPlanoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PlanoRepository planoRepository;

    public List<EmpresaPlanoDTO> buscarTodosPorEmpresa(Long codigoEmpresa, Boolean ativo) {
        Optional<Empresa> empresa = empresaRepository.findById(codigoEmpresa);

        if (!empresa.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }

        EmpresaPlanoPk codigo = new EmpresaPlanoPk();
        codigo.setEmpresa(empresa.get());

        EmpresaPlano consulta = new EmpresaPlano(codigo);

        if (ativo != null) {
            consulta.setAtivo(BooleanEnum.get(ativo));
        }

        return empresaPlanoRepository.findAll(Example.of(consulta))
            .stream()
            .map(EmpresaPlanoDTO::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public EmpresaPlanoDTO buscarPorEmpresaEPlano(Long codigoEmpresa, Long codigoPlano) {
        Optional<Empresa> empresa = empresaRepository.findById(codigoEmpresa);

        if (!empresa.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }

        Optional<Plano> plano = planoRepository.findById(codigoPlano);

        if (!plano.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado");
        }

        EmpresaPlanoPk codigo = new EmpresaPlanoPk();
        codigo.setEmpresa(empresa.get());
        codigo.setPlano(plano.get());

        EmpresaPlano consulta = new EmpresaPlano(codigo);

        Optional<EmpresaPlano> empresaPlano = empresaPlanoRepository.findOne(Example.of(consulta));

        if (empresaPlano.isPresent()) {
            return new EmpresaPlanoDTO(empresaPlano.get());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não possui o plano informado");
    }

    public EmpresaPlanoDTO salvar(Long codigoEmpresa, EmpresaPlanoDTO dto) {
        Optional<Empresa> empresa = empresaRepository.findById(codigoEmpresa);

        if (!empresa.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }

        Optional<Plano> plano = planoRepository.findById(dto.getPlano());

        if (!plano.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado");
        }

        EmpresaPlanoPk codigo = new EmpresaPlanoPk();
        codigo.setEmpresa(empresa.get());
        codigo.setPlano(plano.get());

        EmpresaPlano consulta = new EmpresaPlano(codigo);

        Optional<EmpresaPlano> optional = empresaPlanoRepository.findOne(Example.of(consulta));

        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Plano já está vinculado à empresa");
        }

        EmpresaPlano empresaPlano = dto.converterParaSalvar(codigo);

        empresaPlanoRepository.save(empresaPlano);

        return new EmpresaPlanoDTO(empresaPlano);
    }

    public EmpresaPlanoDTO alterar(Long codigoEmpresa, EmpresaPlanoDTO dto) {
        Optional<Empresa> empresa = empresaRepository.findById(codigoEmpresa);

        if (!empresa.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }

        Optional<Plano> plano = planoRepository.findById(dto.getPlano());

        if (!plano.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado");
        }

        EmpresaPlanoPk codigo = new EmpresaPlanoPk();
        codigo.setEmpresa(empresa.get());
        codigo.setPlano(plano.get());

        EmpresaPlano consulta = new EmpresaPlano(codigo);

        Optional<EmpresaPlano> optional = empresaPlanoRepository.findOne(Example.of(consulta));

        if (optional.isPresent()) {
            EmpresaPlano empresaPlano = optional.get();

            if (dto.getValor() != null) {
                empresaPlano.setValor(dto.getValor());
            }

            if (dto.getAtivo() != null) {
                empresaPlano.setAtivo(BooleanEnum.get(dto.getAtivo()));
            }

            empresaPlanoRepository.save(empresaPlano);

            return new EmpresaPlanoDTO(empresaPlano);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não está vinculado à empresa");
        }
    }
}
