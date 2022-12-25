package com.emarques.seniorapi.domain.model;

import com.emarques.seniorapi.domain.enumerator.TipoItem;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private BigDecimal precoTotalComDesconto;

    @Formula("false")
    private boolean isPrecoTotalAtualizado;

    private Integer quantidade;

    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    public BigDecimal getPrecoTotal() {
        if(!isPrecoTotalAtualizado)
            calcularPrecoTotal();
        return precoTotal;
    }
    public BigDecimal getPrecoTotalComDesconto() {
        if(!isPrecoTotalAtualizado)
            calcularPrecoTotal();
        return precoTotalComDesconto;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calcularPrecoTotal();
    }
    public void calcularPrecoTotal() {
        BigDecimal precoUnitario = this.getPrecoUnitario();
        Integer quantidade = this.getQuantidade();
        BigDecimal descontoAplicado = BigDecimal.valueOf(1);

        if (precoUnitario == null) {
            precoUnitario = BigDecimal.ZERO;
        }

        if (quantidade == null) {
            quantidade = 0;
        }

        if (produto != null
            && produto.getTipoItem() != null
            && produto.getTipoItem().equals(TipoItem.PRODUTO)){
            descontoAplicado = pedido.getDesconto();
        }

        this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
        this.setPrecoTotalComDesconto(precoTotal.multiply(descontoAplicado).setScale(2));
        setPrecoTotalAtualizado(true);
    }


}
