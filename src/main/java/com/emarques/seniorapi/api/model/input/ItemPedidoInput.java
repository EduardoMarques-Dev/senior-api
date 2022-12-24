package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.model.Produto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
