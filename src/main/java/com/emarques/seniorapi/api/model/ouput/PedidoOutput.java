package com.emarques.seniorapi.api.model.ouput;

import com.emarques.seniorapi.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PedidoOutput {

    private UUID id;

    private BigDecimal valorTotal;

    private BigDecimal valorTotalComDesconto;

    private BigDecimal desconto;

    private Endereco enderecoEntrega;

    private OffsetDateTime dataCriacao;

    @JsonIgnoreProperties("pedidoId")
    private List<ItemPedidoOutput> itens = new ArrayList<>();

}
