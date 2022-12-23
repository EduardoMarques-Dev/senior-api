package com.emarques.seniorapi.api.model.ouput;

import com.emarques.seniorapi.domain.enumerator.TipoItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoOutput {
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

    private TipoItem tipoItem;

}
