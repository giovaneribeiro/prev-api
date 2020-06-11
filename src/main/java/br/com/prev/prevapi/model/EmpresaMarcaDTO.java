package br.com.prev.prevapi.model;

import br.com.prev.prevapi.entity.Marca;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpresaMarcaDTO {

    private Long codigo;

    private String nome;

    public EmpresaMarcaDTO(Marca marca) {
        this.codigo = marca.getCodigo();
        this.nome = marca.getNome();
    }
}
