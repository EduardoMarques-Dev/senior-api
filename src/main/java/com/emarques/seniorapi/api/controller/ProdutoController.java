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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProdutoController {

    private ProdutoService produtoService;

    private ProdutoMapper conversor;



    @GetMapping
    public ResponseEntity<Page<ProdutoOutput>> listar(Pageable pageable){
        return ResponseEntity.ok(
                conversor.toOutputCollection(produtoService.listar(pageable))
        );
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutput> buscar(@PathVariable UUID produtoId){
        return ResponseEntity.ok(
                conversor.toOutput(produtoService.buscarOuFalhar(produtoId))
        );
    }

    @GetMapping("/buscar-por")
    public ResponseEntity<Page<ProdutoOutput>> buscarPorNome(@RequestParam String nome, Pageable pageable){
        return ResponseEntity.ok(
                conversor.toOutputCollection(produtoService.buscarPorNome(nome, pageable))
        );
    }

    @GetMapping("/buscar-primeiro")
    public ResponseEntity<ProdutoOutput> buscarPrimeiro(){
        return ResponseEntity.ok(
                conversor.toOutput(produtoService.buscarPrimeiro())
        );
    }

    @PostMapping
    public ResponseEntity<ProdutoOutput> salvar(@RequestBody @Valid ProdutoInput produtoInput){
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
    public ResponseEntity<ProdutoOutput> atualizar(@PathVariable UUID produtoId,
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
    public ResponseEntity<Void> remover(@PathVariable UUID produtoId){
        produtoService.remover(produtoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{produtoId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable UUID produtoId) {
        produtoService.ativar(produtoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{produtoId}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable UUID produtoId) {
        produtoService.inativar(produtoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativarMultiplos(@RequestBody @Valid List<UUID> produtoIds) {
        try {
            produtoService.ativar(produtoIds);
            return ResponseEntity.noContent().build();
        } catch (ProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativarMultiplos(@RequestBody @Valid List<UUID> produtosIds) {
        try {
            produtoService.inativar(produtosIds);
            return ResponseEntity.noContent().build();
        } catch (ProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
