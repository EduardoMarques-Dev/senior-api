package com.emarques.seniorapi.api.model.ouput;

import com.emarques.seniorapi.domain.enumerator.StatusPedido;
import com.emarques.seniorapi.domain.model.Endereco;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoOutput {

    private Long id;
    private String codigo;
    private BigDecimal valorTotal;
    private Endereco enderecoEntrega;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private FormaPagamento formaPagamento;
//
//    @ManyToOne
//    @JoinColumn(name = "usuario_cliente_id", nullable = false)
//    private Usuario cliente;

    private List<ItemPedidoOutput> itens = new ArrayList<>();

}
