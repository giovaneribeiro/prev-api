package br.com.prev.prevapi.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.prev.prevapi.entity.Plano;
import br.com.prev.prevapi.enums.BooleanEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanoDTO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Long codigo;

    private String nome;

    private Boolean ativo;

    private String inclusao;

    public PlanoDTO(Plano plano) {
        this.codigo = plano.getCodigo();
        this.nome = plano.getNome();
        this.ativo = BooleanEnum.SIM.getCodigo().equals(plano.getAtivo());
        this.inclusao = dateFormat.format(plano.getInclusao());
    }

    public Plano converterParaSalvar() {
        Plano plano = new Plano();

        plano.setNome(this.nome);
        plano.setAtivo(BooleanEnum.SIM.getCodigo());
        plano.setInclusao(new Date());

        return plano;
    }
}
