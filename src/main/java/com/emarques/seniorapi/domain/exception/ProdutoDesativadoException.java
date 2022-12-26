package com.emarques.seniorapi.domain.exception;

import java.util.UUID;

public class ProdutoDesativadoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoDesativadoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoDesativadoException(UUID produtoId) {
        super("O Produto com o código '"+produtoId+"' está desativado");
    }


}
