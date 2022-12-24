package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
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

    @PositiveOrZero
    private BigDecimal valorTotal;

    @NotNull
    private Endereco enderecoEntrega;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private FormaPagamento formaPagamento;
//
//    @ManyToOne
//    @JoinColumn(name = "usuario_cliente_id", nullable = false)
//    private Usuario cliente;

    private List<ItemPedidoInput> itens = new ArrayList<>();

}
