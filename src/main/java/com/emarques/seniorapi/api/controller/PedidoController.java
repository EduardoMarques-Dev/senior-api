package com.emarques.seniorapi.api.controller;

import com.emarques.seniorapi.api.mapper.PedidoMapper;
import com.emarques.seniorapi.api.model.input.PedidoInput;
import com.emarques.seniorapi.api.model.ouput.PedidoOutput;
import com.emarques.seniorapi.domain.exception.EntidadeNaoEncontradaException;
import com.emarques.seniorapi.domain.exception.NegocioException;
import com.emarques.seniorapi.domain.exception.ProdutoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.ItemPedido;
import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.service.PedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PedidoController {

    private PedidoService pedidoService;

    private PedidoMapper conversor;

    @GetMapping
    public ResponseEntity<List<PedidoOutput>> listar(){
        return ResponseEntity.ok(
                conversor.toOutputCollection(pedidoService.listar())
        );
    }

    @GetMapping("/{codigoPedido}")
    public ResponseEntity<PedidoOutput> buscar(@PathVariable String codigoPedido) {
        return ResponseEntity.ok(
                conversor.toOutput(pedidoService.buscarOuFalhar(codigoPedido))
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoOutput> salvar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = conversor.toDomain(pedidoInput);

//            novoPedido.setCliente(new Usuario());
//            novoPedido.getCliente().setId(algaSecurity.getUsuarioId());

            novoPedido = pedidoService.salvar(novoPedido);

            return ResponseEntity
                    .created(URI.create("URL"))
                    .body(conversor.toOutput(novoPedido));

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{codigoPedido}")
    public ResponseEntity<PedidoOutput> atualizar(@PathVariable String codigoPedido,
                                                  @RequestBody PedidoInput pedidoInput){
        // Sempre que uma entidade possuir relacionamentos, é possível que o relacionamento passado não exista
        // e isso dispare uma exception
        try {
            Pedido pedido = conversor.toDomain(pedidoInput);

            pedido = pedidoService.atualizar(codigoPedido, pedido);

            return ResponseEntity.ok(conversor.toOutput(pedido));
        } catch (ProdutoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoPedido}")
    public ResponseEntity<Void> remover(@PathVariable String codigoPedido){
        pedidoService.remover(codigoPedido);
        return ResponseEntity.noContent().build();
    }




}
