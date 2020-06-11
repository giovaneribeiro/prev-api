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

import br.com.prev.prevapi.model.EmpresaDTO;
import br.com.prev.prevapi.service.EmpresaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<EmpresaDTO> obter(
        @RequestParam(value = "marca", required = false) Long marca,
        @RequestParam(value = "ativo", required = false) Boolean ativo
    ) {
        return empresaService.buscarTodos(marca, ativo);
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<EmpresaDTO> obterPorCodigo(@PathVariable(value = "codigo") Long codigo) {
        EmpresaDTO empresa = empresaService.buscar(codigo);
        return new ResponseEntity<EmpresaDTO>(empresa, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> salvar(@Valid @RequestBody EmpresaDTO dto) {
        EmpresaDTO empresa = empresaService.salvar(dto);
        return new ResponseEntity<EmpresaDTO>(empresa, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{codigo}")
    public ResponseEntity<EmpresaDTO> alterar(
        @PathVariable(value = "codigo") Long codigo,
        @Valid @RequestBody EmpresaDTO dto
    ) {
        dto.setCodigo(codigo);

        EmpresaDTO empresa = empresaService.alterar(dto);
        return new ResponseEntity<EmpresaDTO>(empresa, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<Void> remover(@PathVariable(value = "codigo") Long codigo) {
        empresaService.remover(codigo);
        return ResponseEntity.noContent().build();
    }
}
