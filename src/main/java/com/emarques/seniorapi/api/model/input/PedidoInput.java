package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoInput {

    @Valid
    @NotNull
    private Endereco enderecoEntrega;

    @PositiveOrZero
    @Max(1)
    private BigDecimal desconto;

    private List<ItemPedidoInput> itens = new ArrayList<>();

}
