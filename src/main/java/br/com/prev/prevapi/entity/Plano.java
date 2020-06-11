package br.com.prev.prevapi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_plano")
    private Long codigo;

    @Column(name = "nm_plano", nullable = false, length = 32)
    private String nome;

    @Column(name = "fl_ativo", length = 1)
    private String ativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_inclusao", updatable = false)
    private Date inclusao;
}
