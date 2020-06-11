package br.com.prev.prevapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.prev.prevapi.model.MarcaDTO;
import br.com.prev.prevapi.service.MarcaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public List<MarcaDTO> obter(
        @RequestParam(value = "ativo", required = false) Boolean ativo
    ) {
        return marcaService.buscarTodos(ativo);
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<MarcaDTO> obterPorCodigo(@PathVariable(value = "codigo") Long codigo) {
        MarcaDTO marca = marcaService.buscar(codigo);
        return new ResponseEntity<MarcaDTO>(marca, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MarcaDTO> salvar(@Valid @RequestBody MarcaDTO dto) {
        MarcaDTO marca = marcaService.salvar(dto);
        return new ResponseEntity<MarcaDTO>(marca, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{codigo}")
    public ResponseEntity<MarcaDTO> alterar(
        @PathVariable(value = "codigo") Long codigo,
        @Valid @RequestBody MarcaDTO dto
    ) {
        dto.setCodigo(codigo);

        MarcaDTO marca = marcaService.alterar(dto);
        return new ResponseEntity<MarcaDTO>(marca, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<Void> remover(@PathVariable(value = "codigo") Long codigo) {
        marcaService.remover(codigo);
        return ResponseEntity.noContent().build();
    }
}
