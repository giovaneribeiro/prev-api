package br.com.prev.prevapi.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.prev.prevapi.entity.Empresa;
import br.com.prev.prevapi.entity.Marca;
import br.com.prev.prevapi.enums.BooleanEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpresaDTO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Long codigo;

    private EmpresaMarcaDTO marca;

    private String razaoSocial;

    private String cnpj;

    private Boolean ativo;

    private String inclusao;

    public EmpresaDTO(Empresa empresa) {
        this.codigo = empresa.getCodigo();

        this.marca = new EmpresaMarcaDTO();
        this.marca.setCodigo(empresa.getMarca().getCodigo());
        this.marca.setNome(empresa.getMarca().getNome());

        this.razaoSocial = empresa.getRazaoSocial();
        this.cnpj = empresa.getCnpj();
        this.ativo = BooleanEnum.SIM.getCodigo().equals(empresa.getAtivo());
        this.inclusao = dateFormat.format(empresa.getInclusao());
    }

    public Empresa converterParaSalvar() {
        Empresa empresa = new Empresa();
        Marca marca = new Marca();

        empresa.setRazaoSocial(this.razaoSocial);
        empresa.setCnpj(this.getCnpj());

        marca.setCodigo(this.getMarca().getCodigo());
        empresa.setMarca(marca);

        empresa.setAtivo(BooleanEnum.SIM.getCodigo());
        empresa.setInclusao(new Date());

        return empresa;
    }
}
