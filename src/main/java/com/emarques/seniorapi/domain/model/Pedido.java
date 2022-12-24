package com.emarques.seniorapi.domain.model;

import com.emarques.seniorapi.domain.enumerator.StatusPedido;
import com.emarques.seniorapi.domain.exception.NegocioException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private BigDecimal valorTotal;
    @Embedded
    private Endereco enderecoEntrega;
    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;
    @CreationTimestamp
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

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.valorTotal = getItens().stream()
                .map(item -> item.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
//        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
//        registerEvent(new PedidoCanceladoEvent(this));
    }

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            getCodigo(), getStatus().getDescricao(),
                            novoStatus.getDescricao()));
        }

        this.status = novoStatus;
    }

//    public BigDecimal getValorTotal() {
//        BigDecimal soma = BigDecimal.ZERO;
//        if (!itens.isEmpty()){
//            for (ItemPedido item : itens){
//                soma.add(item.getPrecoTotal());
//            }
//        }
//        return soma;
//    }
}
