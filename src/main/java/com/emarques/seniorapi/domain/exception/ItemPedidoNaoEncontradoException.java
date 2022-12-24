package com.emarques.seniorapi.domain.exception;

public class ItemPedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ItemPedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ItemPedidoNaoEncontradoException(Long itemPedidoId) {
        this(String.format("Não existe um cadastro de item do pedido com o código %d", itemPedidoId));
    }


}
