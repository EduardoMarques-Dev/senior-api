package com.emarques.seniorapi.api.model.ouput;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemPedidoOutput {
    private UUID id;

    private UUID pedidoId;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private BigDecimal precoTotalComDesconto;

    private Integer quantidade;

    private String observacao;

    private ProdutoOutput produto;

}
