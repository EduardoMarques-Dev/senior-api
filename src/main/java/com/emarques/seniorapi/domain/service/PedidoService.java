package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.EntidadeEmUsoException;
import com.emarques.seniorapi.domain.exception.PedidoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PedidoService {

    private static final String MSG_PEDIDO_EM_USO
            = "O Pedido %d não pode ser removido, pois está em uso";

    PedidoRepository pedidoRepository;

    /*----- CRUD -------*/

    @Transactional
    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido buscarOuFalhar(UUID pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        // LÓGICA
        validarPedido(pedido);
        pedido.calcularValorTotal();

        // PERSISTÊNCIA
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizar(UUID pedidoId, Pedido pedido) {
        // INICIALIZAR
        validarPedido(pedido);
        Pedido pedidoAtual = buscarOuFalhar(pedidoId);

        // LÓGICA
        BeanUtils.copyProperties(pedido, pedidoAtual,"id");
        pedidoAtual.calcularValorTotal();

        // PERSISTÊNCIA
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

    /*----- NEGOCIO -------*/

    @Transactional
    public void confirmar(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido.confirmar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido.cancelar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido.entregar();
    }

    private void validarPedido(Pedido pedido) {
        // VERIFICAR SE AS FOREIGN KEYS DO PEDIDO SÃO VÁLIDAS

//        Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
//        Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
//        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
//        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());
//
//        pedido.getEnderecoEntrega().setCidade(cidade);
//        pedido.setCliente(cliente);
//        pedido.setRestaurante(restaurante);
//        pedido.setFormaPagamento(formaPagamento);
//
//        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
//            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
//                    formaPagamento.getDescricao()));
//        }
    }
}
