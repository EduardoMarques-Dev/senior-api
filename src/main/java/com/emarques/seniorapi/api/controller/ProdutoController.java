package com.emarques.seniorapi.api.controller;

import com.emarques.seniorapi.api.mapper.ProdutoMapper;
import com.emarques.seniorapi.api.model.input.ProdutoInput;
import com.emarques.seniorapi.api.model.ouput.ProdutoOutput;
import com.emarques.seniorapi.domain.exception.NegocioException;
import com.emarques.seniorapi.domain.exception.ProdutoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProdutoController {

    private ProdutoService produtoService;
    private ProdutoMapper conversor;

    @GetMapping
    public ResponseEntity<List<ProdutoOutput>> listar(){
        return ResponseEntity.ok(
                conversor.toOutputCollection(produtoService.listar())
        );
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutput> buscar(@PathVariable Long produtoId){
        return ResponseEntity.ok(
                conversor.toOutput(produtoService.buscarOuFalhar(produtoId))
        );
    }

    @PostMapping
    public ResponseEntity<ProdutoOutput> salvar(@RequestBody @Valid ProdutoInput produtoInput){
        // Sempre que uma entidade possuir relacionamentos, é possível que o relacionamento passado não exista
        // e isso dispare uma exception
        try {
            Produto produto = conversor.toDomain(produtoInput);

            produto = produtoService.salvar(produto);

            return ResponseEntity
                    .created(URI.create("URL"))
                    .body(conversor.toOutput(produto));
        } catch (ProdutoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutput> atualizar(@PathVariable Long produtoId,
                                                   @RequestBody @Valid ProdutoInput produtoInput){
        // Sempre que uma entidade possuir relacionamentos, é possível que o relacionamento passado não exista
        // e isso dispare uma exception
        try {
            Produto produto = conversor.toDomain(produtoInput);

            produto = produtoService.atualizar(produtoId, produto);

            return ResponseEntity.ok(conversor.toOutput(produto));
        } catch (ProdutoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> remover(@PathVariable Long produtoId){
        produtoService.remover(produtoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{produtoId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long produtoId) {
        produtoService.ativar(produtoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{produtoId}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long produtoId) {
        produtoService.inativar(produtoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativarMultiplos(@RequestBody @Valid List<Long> produtoIds) {
        try {
            produtoService.ativar(produtoIds);
            return ResponseEntity.noContent().build();
        } catch (ProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativarMultiplos(@RequestBody @Valid List<Long> restauranteIds) {
        try {
            produtoService.inativar(restauranteIds);
            return ResponseEntity.noContent().build();
        } catch (ProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
