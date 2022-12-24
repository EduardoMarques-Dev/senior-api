package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.EntidadeEmUsoException;
import com.emarques.seniorapi.domain.exception.ItemPedidoNaoEncontradoException;
import com.emarques.seniorapi.domain.exception.ProdutoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.ItemPedido;
import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.repository.ItemPedidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ItemPedidoService {

    private static final String MSG_ITEM_PEDIDO_EM_USO
            = "O Item do Pedido %d não pode ser removido, pois está em uso";

    private ItemPedidoRepository itemPedidoRepository;
    private ProdutoService produtoService;

    private PedidoService pedidoService;

    @Transactional
    public List<ItemPedido> listar(){
        return itemPedidoRepository.findAll();
    }

    @Transactional
    public ItemPedido buscarOuFalhar(UUID itemPedidoId) {
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
    public ItemPedido atualizar(UUID itemPedidoId, ItemPedido itemPedido){
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
    public void remover(UUID itemPedidoId){
        try{
            itemPedidoRepository.deleteById(itemPedidoId);
            itemPedidoRepository.flush();
        } catch (
                EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException(itemPedidoId);
        } catch (
                DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ITEM_PEDIDO_EM_USO, itemPedidoId));
        }
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
