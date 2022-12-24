package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.enumerator.StatusPedido;
import com.emarques.seniorapi.domain.exception.NegocioException;
import com.emarques.seniorapi.domain.model.Endereco;
import com.emarques.seniorapi.domain.model.ItemPedido;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoInput {

    private Long id;

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
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

    private List<ItemPedidoInput> itens = new ArrayList<>();

}
