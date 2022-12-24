package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.PedidoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    PedidoRepository pedidoRepository;

    /*----- CRUD -------*/

    @Transactional
    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        // LÓGICA
        validarPedido(pedido);
        pedido.calcularValorTotal();

        // PERSISTÊNCIA
        pedido = pedidoRepository.save(pedido);
        return pedido;
    }

    @Transactional
    public Pedido atualizar(Long pedidoId, Pedido pedido) {
        // INICIALIZAR
        validarPedido(pedido);
        Pedido pedidoAtual = buscarOuFalhar(pedidoId);

        // LÓGICA
        BeanUtils.copyProperties(pedido, pedidoAtual,
                "id");
        pedidoAtual.calcularValorTotal();

        // PERSISTÊNCIA
        return pedidoRepository.save(pedidoAtual);
    }

    @Transactional
    public void remover(Long pedidoId){
        pedidoRepository.deleteById(pedidoId);
        pedidoRepository.flush();
    }

    /*----- NEGOCIO -------*/

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido.confirmar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido.cancelar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(Long pedidoId) {
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

//    private void validarItens(Pedido pedido) {
//        // VERIFICAR SE AS FOREIGN KEYS DOS ITENS DO PEDIDO SÃO VÁLIDAS
//
//        pedido.getItens().forEach(item -> {
//            Produto produto = produtoService.buscarOuFalhar(item.getProduto().getId());
//            item.setPedido(pedido);
//            item.setProduto(produto);
//            item.setPrecoUnitario(produto.getPreco());
//        });
//    }
}
