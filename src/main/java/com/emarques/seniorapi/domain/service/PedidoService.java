package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.EntidadeEmUsoException;
import com.emarques.seniorapi.domain.exception.PedidoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PedidoService {

    private static final String MSG_PEDIDO_EM_USO
            = "O Pedido %d não pode ser removido, pois está em uso";

    PedidoRepository pedidoRepository;



    @Transactional
    public Page<Pedido> listar(Pageable pageable){
        return pedidoRepository.findAll(pageable);
    }

    @Transactional
    public Pedido buscarOuFalhar(UUID pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizar(UUID pedidoId, Pedido pedido) {
        Pedido pedidoAtual = buscarOuFalhar(pedidoId);

        BeanUtils.copyProperties(pedido, pedidoAtual,"id");
        pedidoAtual.calcularValorTotal();

        return pedidoRepository.save(pedidoAtual);
    }

    @Transactional
    public void remover(UUID pedidoId){
        try {
            pedidoRepository.deleteById(pedidoId);
            pedidoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PedidoNaoEncontradoException(pedidoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PEDIDO_EM_USO, pedidoId));
        }
    }



    @Transactional
    public void aplicarDesconto(BigDecimal desconto, UUID pedidoId){
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido.setDesconto(desconto);
    }

    @Transactional
    public void abrirPedido(UUID pedidoId){
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido.abrir();
    }

    @Transactional
    public void fecharPedido(UUID pedidoId){
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido.fechar();
    }
}
