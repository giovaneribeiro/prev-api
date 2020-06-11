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

import br.com.prev.prevapi.entity.Plano;
import br.com.prev.prevapi.enums.BooleanEnum;
import br.com.prev.prevapi.model.PlanoDTO;
import br.com.prev.prevapi.repository.EmpresaPlanoRepository;
import br.com.prev.prevapi.repository.PlanoRepository;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private EmpresaPlanoRepository empresaPlanoRepository;

    public List<PlanoDTO> buscarTodos(Boolean ativo) {
        Plano consulta = new Plano();

        if (ativo != null) {
            consulta.setAtivo(BooleanEnum.get(ativo));
        }

        return planoRepository.findAll(Example.of(consulta))
            .stream()
            .map(PlanoDTO::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public PlanoDTO buscar(Long codigo) {
        Optional<Plano> optional = planoRepository.findById(codigo);

        if (optional.isPresent()) {
            return new PlanoDTO(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado");
        }
    }

    public PlanoDTO salvar(PlanoDTO dto) {
        Plano plano = dto.converterParaSalvar();

        planoRepository.save(plano);

        return new PlanoDTO(plano);
    }

    public PlanoDTO alterar(PlanoDTO dto) {
        Optional<Plano> optional = planoRepository.findById(dto.getCodigo());

        if (optional.isPresent()) {
            Plano plano = optional.get();

            if (dto.getNome() != null) {
                plano.setNome(dto.getNome());
            }

            if (dto.getAtivo() != null) {
                plano.setAtivo(BooleanEnum.get(dto.getAtivo()));
            }

            planoRepository.save(plano);

            return new PlanoDTO(plano);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado");
        }
    }

    public void remover(Long codigo) {
        Optional<Plano> optional = planoRepository.findById(codigo);

        if (optional.isPresent()) {
            Plano plano = optional.get();

            long quantidade = empresaPlanoRepository.countByCodigoPlano(plano);

            if (quantidade == 0) {
                planoRepository.delete(plano);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este plano não pode ser removido, o mesmo possui empresas vinculadas");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado");
        }
    }
}
