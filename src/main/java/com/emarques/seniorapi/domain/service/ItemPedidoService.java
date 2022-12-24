package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.ItemPedidoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.ItemPedido;
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

    @Transactional
    public List<ItemPedido> listar(){
        return itemPedidoRepository.findAll();
    }

    @Transactional
    public ItemPedido buscar(Long itemPedidoId){
        return buscarOuFalhar(itemPedidoId);
    }

    @Transactional
    public ItemPedido salvar(ItemPedido itemPedido){
        return itemPedidoRepository.save(itemPedido);
    }

    @Transactional
    public ItemPedido atualizar(Long itemPedidoId, ItemPedido itemPedido){
        ItemPedido itemPedidoAtual = buscarOuFalhar(itemPedidoId);

        BeanUtils.copyProperties(itemPedido,itemPedidoAtual, "id");

        return itemPedidoRepository.save(itemPedidoAtual);
    }

    @Transactional
    public void remover(Long itemPedidoId){
        itemPedidoRepository.deleteById(itemPedidoId);
        itemPedidoRepository.flush();
    }

    public ItemPedido buscarOuFalhar(Long itemPedidoId) {
        return itemPedidoRepository.findById(itemPedidoId)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(itemPedidoId));
    }


}
