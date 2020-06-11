package br.com.prev.prevapi.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.prev.prevapi.entity.Marca;
import br.com.prev.prevapi.enums.BooleanEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarcaDTO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Long codigo;

    private String nome;

    private Boolean ativo;

    private String inclusao;

    public MarcaDTO(Marca marca) {
        this.codigo = marca.getCodigo();
        this.nome = marca.getNome();
        this.ativo = BooleanEnum.SIM.getCodigo().equals(marca.getAtivo());
        this.inclusao = dateFormat.format(marca.getInclusao());
    }

    public Marca converterParaSalvar() {
        Marca marca = new Marca();

        marca.setNome(this.nome);
        marca.setAtivo(BooleanEnum.SIM.getCodigo());
        marca.setInclusao(new Date());

        return marca;
    }
}
