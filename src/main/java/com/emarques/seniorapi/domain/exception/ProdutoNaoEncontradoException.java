package com.emarques.seniorapi.domain.exception;

import java.util.UUID;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(UUID produtoId) {
        this(String.format("Não existe um cadastro de produto com o identificador %d", produtoId));
    }


}
