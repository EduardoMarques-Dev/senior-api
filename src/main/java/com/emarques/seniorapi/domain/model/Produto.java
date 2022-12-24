package com.emarques.seniorapi.domain.model;

import com.emarques.seniorapi.domain.enumerator.TipoItem;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private TipoItem tipoItem;

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }
}