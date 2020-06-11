package br.com.prev.prevapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prev.prevapi.entity.Empresa;
import br.com.prev.prevapi.entity.Marca;
import br.com.prev.prevapi.enums.BooleanEnum;
import br.com.prev.prevapi.model.EmpresaDTO;
import br.com.prev.prevapi.repository.EmpresaPlanoRepository;
import br.com.prev.prevapi.repository.EmpresaRepository;
import br.com.prev.prevapi.repository.MarcaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private EmpresaPlanoRepository empresaPlanoRepository;

    public List<EmpresaDTO> buscarTodos(Long codigoMarca, Boolean ativo) {
        Empresa consulta = new Empresa();

        if (codigoMarca != null) {
            Marca marca = new Marca();
            marca.setCodigo(codigoMarca);

            consulta.setMarca(marca);
        }

        if (ativo != null) {
            consulta.setAtivo(BooleanEnum.get(ativo));
        }

        return empresaRepository.findAll(Example.of(consulta))
            .stream()
            .map(EmpresaDTO::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public EmpresaDTO buscar(Long codigo) {
        Optional<Empresa> optional = empresaRepository.findById(codigo);

        if (optional.isPresent()) {
            return new EmpresaDTO(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }
    }

    public EmpresaDTO salvar(EmpresaDTO dto) {
        Empresa empresa = dto.converterParaSalvar();

        try {
            empresaRepository.save(empresa);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma empresa cadastrada com estes mesmos parâmetros");
        }

        return new EmpresaDTO(empresa);
    }

    public EmpresaDTO alterar(EmpresaDTO dto) {
        Optional<Empresa> optional = empresaRepository.findById(dto.getCodigo());

        if (optional.isPresent()) {
            Empresa empresa = optional.get();

            if (dto.getMarca() != null && dto.getMarca().getCodigo() != null) {
                Optional<Marca> marca = marcaRepository.findById(dto.getMarca().getCodigo());

                if (marca.isPresent()) {
                    empresa.setMarca(marca.get());
                }
            }

            if (dto.getRazaoSocial() != null) {
                empresa.setRazaoSocial(dto.getRazaoSocial());
            }

            if (dto.getCnpj() != null) {
                empresa.setCnpj(dto.getCnpj());
            }

            if (dto.getAtivo() != null) {
                empresa.setAtivo(BooleanEnum.get(dto.getAtivo()));
            }

            try {
                empresaRepository.save(empresa);
            } catch (DataIntegrityViolationException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma empresa cadastrada com estes mesmos parâmetros");
            }

            return new EmpresaDTO(empresa);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }
    }

    public void remover(Long codigo) {
        Optional<Empresa> optional = empresaRepository.findById(codigo);

        if (optional.isPresent()) {
            Empresa empresa = optional.get();

            empresaRepository.delete(empresa);
            empresaPlanoRepository.deleteAll(empresaPlanoRepository.findByCodigoEmpresa(empresa));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }
    }
}
