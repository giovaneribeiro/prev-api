package br.com.prev.prevapi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tb_empresa_plano")
public class EmpresaPlano {

    @EmbeddedId
    private EmpresaPlanoPk codigo;

    @Column(name = "vl_plano", nullable = false)
    private Double valor;

    @Column(name = "fl_ativo", length = 1)
    private String ativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_inclusao", updatable = false)
    private Date inclusao;

    public EmpresaPlano (EmpresaPlanoPk codigo) {
        this.codigo = codigo;
    }
}
