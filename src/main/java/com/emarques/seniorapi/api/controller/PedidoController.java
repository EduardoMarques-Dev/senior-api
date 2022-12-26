package com.emarques.seniorapi.api.controller;

import com.emarques.seniorapi.api.mapper.PedidoMapper;
import com.emarques.seniorapi.api.model.input.PedidoInput;
import com.emarques.seniorapi.api.model.ouput.PedidoOutput;
import com.emarques.seniorapi.domain.exception.EntidadeNaoEncontradaException;
import com.emarques.seniorapi.domain.exception.NegocioException;
import com.emarques.seniorapi.domain.exception.ProdutoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.domain.service.PedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PedidoController {

    private PedidoService pedidoService;

    private PedidoMapper conversor;



    @GetMapping
    public ResponseEntity<Page<PedidoOutput>> listar(Pageable pageable){
        return ResponseEntity.ok(
                conversor.toOutputCollection(pedidoService.listar(pageable))
        );
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoOutput> buscar(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(
                conversor.toOutput(pedidoService.buscarOuFalhar(pedidoId))
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoOutput> salvar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = conversor.toDomain(pedidoInput);

            novoPedido = pedidoService.salvar(novoPedido);

            return ResponseEntity
                    .created(URI.create("URL"))
                    .body(conversor.toOutput(novoPedido));

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{pedidoId}")
    public ResponseEntity<PedidoOutput> atualizar(@PathVariable UUID pedidoId,
                                                  @RequestBody @Valid PedidoInput pedidoInput){
        try {
            Pedido pedido = conversor.toDomain(pedidoInput);

            pedido = pedidoService.atualizar(pedidoId, pedido);

            return ResponseEntity.ok(conversor.toOutput(pedido));
        } catch (ProdutoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Void> remover(@PathVariable UUID pedidoId){
        pedidoService.remover(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{pedidoId}/aplicar-desconto")
    public ResponseEntity<Void> aplicarDesconto(@PathVariable UUID pedidoId, @RequestParam BigDecimal desconto){
        pedidoService.aplicarDesconto(desconto, pedidoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{pedidoId}/abrir-pedido")
    public ResponseEntity<Void> abrirPedido(@PathVariable UUID pedidoId){
        pedidoService.abrirPedido(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{pedidoId}/fechar-pedido")
    public ResponseEntity<Void> fecharPedido(@PathVariable UUID pedidoId){
        pedidoService.fecharPedido(pedidoId);
        return ResponseEntity.noContent().build();
    }

}
