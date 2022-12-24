package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.model.Produto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoInput {
    private Long id;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private Integer quantidade;
    private String observacao;
    private Pedido pedido;
    private Produto produto;

}
