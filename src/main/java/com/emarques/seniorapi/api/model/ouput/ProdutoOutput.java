package com.emarques.seniorapi.api.model.ouput;

import com.emarques.seniorapi.domain.enumerator.TipoItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoOutput {

    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo;
    private TipoItem tipoItem;

}
