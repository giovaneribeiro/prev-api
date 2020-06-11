package br.com.prev.prevapi.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class EmpresaPlanoPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "cd_empresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "cd_plano")
    private Plano plano;
}
