package br.com.prev.prevapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.prev.prevapi.model.EmpresaPlanoDTO;
import br.com.prev.prevapi.service.EmpresaPlanoService;

@CrossOrigin(origins = "*")
@RestController
public class EmpresaPlanoController {

    @Autowired
    private EmpresaPlanoService empresaPlanoService;

    @GetMapping(value = "/empresas/{empresa}/planos")
    public List<EmpresaPlanoDTO> obterPorEmpresaCodigo(
        @PathVariable(value = "empresa") Long codigoEmpresa,
        @RequestParam(value = "ativo", required = false) Boolean ativo
    ) {
        return empresaPlanoService.buscarTodosPorEmpresa(codigoEmpresa, ativo);
    }

    @GetMapping(value = "/empresas/{empresa}/planos/{plano}")
    public ResponseEntity<EmpresaPlanoDTO> obterPorEmpresaCodigoEPlanoCodigo(
        @PathVariable(value = "empresa") Long codigoEmpresa,
        @PathVariable(value = "plano") Long codigoPlano
    ) {
        EmpresaPlanoDTO empresaPlano = empresaPlanoService.buscarPorEmpresaEPlano(codigoEmpresa, codigoPlano);
        return new ResponseEntity<EmpresaPlanoDTO>(empresaPlano, HttpStatus.OK);
    }

    @PostMapping(value = "/empresas/{empresa}/planos")
    public ResponseEntity<EmpresaPlanoDTO> salvar(
        @PathVariable(value = "empresa") Long codigoEmpresa,
        @Valid @RequestBody EmpresaPlanoDTO dto
    ) {
        EmpresaPlanoDTO empresaPlano = empresaPlanoService.salvar(codigoEmpresa, dto);
        return new ResponseEntity<EmpresaPlanoDTO>(empresaPlano, HttpStatus.CREATED);
    }

    @PutMapping(value = "/empresas/{empresa}/planos")
    public ResponseEntity<EmpresaPlanoDTO> alterar(
        @PathVariable(value = "empresa") Long codigoEmpresa,
        @Valid @RequestBody EmpresaPlanoDTO dto
    ) {
        EmpresaPlanoDTO empresaPlano = empresaPlanoService.alterar(codigoEmpresa, dto);
        return new ResponseEntity<EmpresaPlanoDTO>(empresaPlano, HttpStatus.OK);
    }
}
