package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.model.Produto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
    @Min(1)
    private Integer quantidade;

    private String observacao;

    @NotNull
    private Pedido pedido;

    @NotNull
    private Produto produto;

}
