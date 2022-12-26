package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.model.Endereco;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoInput {

    @NotNull
    private Endereco enderecoEntrega;

    private BigDecimal desconto;

    private List<ItemPedidoInput> itens = new ArrayList<>();

}
