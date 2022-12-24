package com.emarques.seniorapi.api.model.input;

import com.emarques.seniorapi.domain.enumerator.TipoItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @PositiveOrZero
    @NotNull
    private BigDecimal preco;

    @NotNull
    private Boolean ativo;

    @NotNull
    private TipoItem tipoItem;

}
