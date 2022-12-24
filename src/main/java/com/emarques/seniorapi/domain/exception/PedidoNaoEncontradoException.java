package com.emarques.seniorapi.domain.exception;

import java.util.UUID;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(UUID pedidoId) {
        this(String.format("Não existe um cadastro de pedido com o código %d", pedidoId));
    }
}
