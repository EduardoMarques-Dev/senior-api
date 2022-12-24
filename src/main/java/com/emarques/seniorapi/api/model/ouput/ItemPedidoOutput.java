package com.emarques.seniorapi.api.model.ouput;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoOutput {
    private Long id;

    private Long pedidoId;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private Integer quantidade;

    private String observacao;

    private ProdutoOutput produto;

}
