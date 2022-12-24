package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.enumerator.TipoItem;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo;

    @Enumerated(value = EnumType.STRING)
    private TipoItem tipoItem;

}
