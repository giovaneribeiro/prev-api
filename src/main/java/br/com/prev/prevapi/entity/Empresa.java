package br.com.prev.prevapi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_empresa")
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "cd_marca", nullable = false)
    private Marca marca;

    @Column(name = "nm_razao_social", nullable = false)
    private String razaoSocial;

    @Column(name = "nr_cnpj", nullable = false, length = 18)
    private String cnpj;

    @Column(name = "fl_ativo", length = 1)
    private String ativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_inclusao", updatable = false)
    private Date inclusao;
}
