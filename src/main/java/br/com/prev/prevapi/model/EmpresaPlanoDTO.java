package br.com.prev.prevapi.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.prev.prevapi.entity.EmpresaPlano;
import br.com.prev.prevapi.entity.EmpresaPlanoPk;
import br.com.prev.prevapi.entity.Plano;
import br.com.prev.prevapi.enums.BooleanEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpresaPlanoDTO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Long plano;

    private String nomePlano;

    private Double valor;

    private Boolean ativo;

    private String inclusao;

    public EmpresaPlanoDTO(EmpresaPlano empresaPlano) {
        if (empresaPlano.getCodigo() != null) {
            Plano plano = empresaPlano.getCodigo().getPlano();

            if (plano != null) {
                this.plano = plano.getCodigo();
                this.nomePlano = plano.getNome();
            }
        }

        this.valor = empresaPlano.getValor();
        this.ativo = BooleanEnum.SIM.getCodigo().equals(empresaPlano.getAtivo());
        this.inclusao = dateFormat.format(empresaPlano.getInclusao());
    }

    public EmpresaPlano converterParaSalvar(EmpresaPlanoPk codigo) {
        EmpresaPlano empresaPlano = new EmpresaPlano(codigo);

        empresaPlano.setValor(this.valor);
        empresaPlano.setAtivo(BooleanEnum.SIM.getCodigo());
        empresaPlano.setInclusao(new Date());

        return empresaPlano;
    }
}
