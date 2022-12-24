package com.emarques.seniorapi.api.controller;

import com.emarques.seniorapi.api.mapper.ItemPedidoMapper;
import com.emarques.seniorapi.api.model.input.ItemPedidoInput;
import com.emarques.seniorapi.api.model.ouput.ItemPedidoOutput;
import com.emarques.seniorapi.domain.exception.NegocioException;
import com.emarques.seniorapi.domain.exception.ProdutoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.ItemPedido;
import com.emarques.seniorapi.domain.service.ItemPedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/itemPedidos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ItemPedidoController {

    private ItemPedidoService itemPedidoService;

    private ItemPedidoMapper conversor;

    @GetMapping
    public ResponseEntity<List<ItemPedidoOutput>> listar(){
        return ResponseEntity.ok(
                conversor.toOutputCollection(itemPedidoService.listar())
        );
    }

    @GetMapping("/{itemPedidoId}")
    public ResponseEntity<ItemPedidoOutput> buscar(@PathVariable Long itemPedidoId){
        return ResponseEntity.ok(
                conversor.toOutput(itemPedidoService.buscarOuFalhar(itemPedidoId))
        );
    }

    @PostMapping
    public ResponseEntity<ItemPedidoOutput> salvar(@RequestBody @Valid ItemPedidoInput itemPedidoInput){
        // Sempre que uma entidade possuir relacionamentos, é possível que o relacionamento passado não exista
        // e isso dispare uma exception
        try {
            ItemPedido itemPedido = conversor.toDomain(itemPedidoInput);

            itemPedido = itemPedidoService.salvar(itemPedido);

            return ResponseEntity
                    .created(URI.create("URL"))
                    .body(conversor.toOutput(itemPedido));
        } catch (ProdutoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{itemPedidoId}")
    public ResponseEntity<ItemPedidoOutput> atualizar(@PathVariable Long itemPedidoId,
                                                      @RequestBody @Valid ItemPedidoInput itemPedidoInput){
        // Sempre que uma entidade possuir relacionamentos, é possível que o relacionamento passado não exista
        // e isso dispare uma exception
        try {
            ItemPedido produto = conversor.toDomain(itemPedidoInput);

            produto = itemPedidoService.atualizar(itemPedidoId, produto);

            return ResponseEntity.ok(conversor.toOutput(produto));
        } catch (ProdutoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{itemPedidoId}")
    public ResponseEntity<Void> remover(@PathVariable Long itemPedidoId){
        itemPedidoService.remover(itemPedidoId);
        return ResponseEntity.noContent().build();
    }
}
