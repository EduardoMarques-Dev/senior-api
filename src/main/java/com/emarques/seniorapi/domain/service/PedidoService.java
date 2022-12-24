package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.PedidoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.ItemPedido;
import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.repository.ItemPedidoRepository;
import com.emarques.seniorapi.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    ProdutoService produtoService;

    PedidoRepository pedidoRepository;

    ItemPedidoRepository itemPedidoRepository;

    /*----- CRUD -------*/

    @Transactional
    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido buscarOuFalhar(String codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

//        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

//        List<ItemPedido> itemPedidos = itemPedidoRepository.saveAll(pedido.getItens());
//        pedido.setItens(itemPedidos);

        pedido = pedidoRepository.save(pedido);

        return pedido;
    }

    @Transactional
    public Pedido atualizar(String codigoPedido, Pedido pedido) {
        Pedido pedidoAtual = buscarOuFalhar(codigoPedido);

        BeanUtils.copyProperties(pedido, pedidoAtual,
                "id");

        validarPedido(pedidoAtual);
        validarItens(pedidoAtual);
//        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedidoAtual.calcularValorTotal();

        return pedidoRepository.save(pedidoAtual);
    }

    @Transactional
    public void remover(String codigoPedido){
        pedidoRepository.deleteByCodigo(codigoPedido);
        pedidoRepository.flush();
    }

    /*----- NEGOCIO -------*/

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = buscarOuFalhar(codigoPedido);
        pedido.cancelar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = buscarOuFalhar(codigoPedido);
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

    private void validarItens(Pedido pedido) {
        // VERIFICAR SE AS FOREIGN KEYS DOS ITENS DO PEDIDO SÃO VÁLIDAS

        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.buscarOuFalhar(item.getProduto().getId());
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
}
