package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.ItemPedidoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.ItemPedido;
import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.repository.ItemPedidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemPedidoService {

    private ItemPedidoRepository itemPedidoRepository;
    private ProdutoService produtoService;

    private PedidoService pedidoService;

    @Transactional
    public List<ItemPedido> listar(){
        return itemPedidoRepository.findAll();
    }

    @Transactional
    public ItemPedido buscarOuFalhar(Long itemPedidoId) {
        return itemPedidoRepository.findById(itemPedidoId)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(itemPedidoId));
    }

    @Transactional
    public ItemPedido salvar(ItemPedido itemPedido){
        // INICIALIZAR
        Pedido pedido = validarItensDoPedido(itemPedido);

        // PERSISTÊNCIA
        itemPedido = itemPedidoRepository.save(itemPedido);
        pedidoService.atualizar(pedido.getId(),pedido);

        return itemPedido;
    }

    @Transactional
    public ItemPedido atualizar(Long itemPedidoId, ItemPedido itemPedido){
        // INICIALIZAR
        Pedido pedido = validarItensDoPedido(itemPedido);
        ItemPedido itemPedidoAtual = buscarOuFalhar(itemPedidoId);

        // LÓGICA
        BeanUtils.copyProperties(itemPedido,itemPedidoAtual, "id");

        // PERSISTÊNCIA
        itemPedidoAtual = itemPedidoRepository.save(itemPedidoAtual);
        pedidoService.atualizar(pedido.getId(),pedido);

        return itemPedidoAtual;
    }

    @Transactional
    public void remover(Long itemPedidoId){
        itemPedidoRepository.deleteById(itemPedidoId);
        itemPedidoRepository.flush();
    }

    private Pedido validarItensDoPedido(ItemPedido itemPedido) {
        Produto produto = produtoService.buscarOuFalhar(itemPedido.getProduto().getId());
        Pedido pedido = pedidoService.buscarOuFalhar(itemPedido.getPedido().getId());

        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setPrecoUnitario(produto.getPreco());

        return pedido;
    }



}
