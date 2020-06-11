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

import br.com.prev.prevapi.entity.Marca;
import br.com.prev.prevapi.enums.BooleanEnum;
import br.com.prev.prevapi.model.MarcaDTO;
import br.com.prev.prevapi.repository.EmpresaRepository;
import br.com.prev.prevapi.repository.MarcaRepository;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<MarcaDTO> buscarTodos(Boolean ativo) {
        Marca consulta = new Marca();

        if (ativo != null) {
            consulta.setAtivo(BooleanEnum.get(ativo));
        }

        return marcaRepository.findAll(Example.of(consulta))
            .stream()
            .map(MarcaDTO::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public MarcaDTO buscar(Long codigo) {
        Optional<Marca> optional = marcaRepository.findById(codigo);

        if (optional.isPresent()) {
            return new MarcaDTO(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca não encontrada");
        }
    }

    public MarcaDTO salvar(MarcaDTO dto) {
        Marca marca = dto.converterParaSalvar();

        marcaRepository.save(marca);

        return new MarcaDTO(marca);
    }

    public MarcaDTO alterar(MarcaDTO dto) {
        Optional<Marca> optional = marcaRepository.findById(dto.getCodigo());

        if (optional.isPresent()) {
            Marca marca = optional.get();

            if (dto.getNome() != null) {
                marca.setNome(dto.getNome());
            }

            if (dto.getAtivo() != null) {
                marca.setAtivo(BooleanEnum.get(dto.getAtivo()));
            }

            marcaRepository.save(marca);

            return new MarcaDTO(marca);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca não encontrada");
        }
    }

    public void remover(Long codigo) {
        Optional<Marca> optional = marcaRepository.findById(codigo);

        if (optional.isPresent()) {
            Marca marca = optional.get();

            long quantidade = empresaRepository.countByMarcaCodigo(codigo);

            if (quantidade == 0) {
                marcaRepository.delete(marca);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esta marca não pode ser removida, a mesma possui empresas vinculadas");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca não encontrada");
        }
    }
}
