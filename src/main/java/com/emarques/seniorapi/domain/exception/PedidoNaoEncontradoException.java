package com.emarques.seniorapi.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long itemPedidoId) {
        this(String.format("Não existe um cadastro de pedido com o código %d", itemPedidoId));
    }
}
