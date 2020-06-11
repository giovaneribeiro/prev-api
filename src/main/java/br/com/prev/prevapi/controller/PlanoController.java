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

import br.com.prev.prevapi.model.PlanoDTO;
import br.com.prev.prevapi.service.PlanoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/planos")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @GetMapping
    public List<PlanoDTO> obter(@RequestParam(value = "ativo", required = false) Boolean ativo) {
        return planoService.buscarTodos(ativo);
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<PlanoDTO> obterPorCodigo(@PathVariable(value = "codigo") Long codigo) {
        PlanoDTO plano = planoService.buscar(codigo);
        return new ResponseEntity<PlanoDTO>(plano, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlanoDTO> salvar(@Valid @RequestBody PlanoDTO dto) {
        PlanoDTO plano = planoService.salvar(dto);
        return new ResponseEntity<PlanoDTO>(plano, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{codigo}")
    public ResponseEntity<PlanoDTO> alterar(@PathVariable(value = "codigo") Long codigo, @Valid @RequestBody PlanoDTO dto) {
        dto.setCodigo(codigo);

        PlanoDTO plano = planoService.alterar(dto);
        return new ResponseEntity<PlanoDTO>(plano, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<Void> remover(@PathVariable(value = "codigo") Long codigo) {
        planoService.remover(codigo);
        return ResponseEntity.noContent().build();
    }
}
