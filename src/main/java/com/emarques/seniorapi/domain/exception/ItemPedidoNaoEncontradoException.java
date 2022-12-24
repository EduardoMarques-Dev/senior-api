package com.emarques.seniorapi.domain.exception;

import java.util.UUID;

public class ItemPedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ItemPedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ItemPedidoNaoEncontradoException(UUID itemPedidoId) {
        this("Não existe um cadastro de item do pedido com o código: "+ itemPedidoId.toString());
    }


}
