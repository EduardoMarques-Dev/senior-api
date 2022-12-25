package com.emarques.seniorapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private BigDecimal valorTotal;

    private BigDecimal valorTotalComDesconto;

    private BigDecimal desconto;

    @Formula("false")
    private boolean isValorTotalAtualizado;

    @Embedded
    private Endereco enderecoEntrega;

    private Boolean aberto = true;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    public BigDecimal getValorTotal() {
        if(!isValorTotalAtualizado)
            calcularValorTotal();
        return valorTotal;
    }
    public BigDecimal getValorTotalComDesconto() {
        if(!isValorTotalAtualizado)
            calcularValorTotal();
        return valorTotalComDesconto;
    }
    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
        calcularValorTotal();
    }
    public void calcularValorTotal() {
        valorTotal = getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        valorTotalComDesconto = getItens().stream()
                .map(ItemPedido::getPrecoTotalComDesconto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        setValorTotalAtualizado(true);
    }

    public BigDecimal getDesconto() {
        if (getAberto() && desconto != null){
            return desconto;
        }
        return BigDecimal.ONE;
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {setAberto(false);}

}
