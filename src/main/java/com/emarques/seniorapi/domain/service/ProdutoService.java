package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.EntidadeEmUsoException;
import com.emarques.seniorapi.domain.exception.NegocioException;
import com.emarques.seniorapi.domain.exception.ProdutoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProdutoService {

    private static final String MSG_PRODUTO_EM_USO
            = "O Produto %d não pode ser removido, pois está em uso";

    private ProdutoRepository produtoRepository;



    @Transactional
    public Page<Produto> listar(Pageable pageable){
        return produtoRepository.findAll(pageable);
    }

    @Transactional
    public Produto buscarOuFalhar(UUID produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }

    @Transactional
    public Page<Produto> buscarPorNome(String produtoNome, Pageable pageable) {
        return produtoRepository.findByNomeContainingIgnoreCase(produtoNome, pageable);
    }

    @Transactional
    public Produto buscarPrimeiro() {
        return produtoRepository.buscarPrimeiro()
                .orElseThrow(() -> new NegocioException("Não existem produtos cadastrados"));
    }

    @Transactional
    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizar(UUID produtoId, Produto produto){
        Produto produtoAtual = buscarOuFalhar(produtoId);

        BeanUtils.copyProperties(produto,produtoAtual, "id");

        return produtoRepository.save(produtoAtual);
    }

    @Transactional
    public void remover(UUID produtoId){
        try {
            produtoRepository.deleteById(produtoId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException(produtoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, produtoId));
        }
    }



    @Transactional
    public void ativar(UUID produtoId) {
        Produto produtoAtual = buscarOuFalhar(produtoId);
        produtoAtual.ativar();
    }

    @Transactional
    public void inativar(UUID produtoId) {
        Produto produtoAtual = buscarOuFalhar(produtoId);

        produtoAtual.inativar();
    }

    @Transactional
    public void ativar(List<UUID> produtosId) {
        produtosId.forEach(this::ativar);
    }

    @Transactional
    public void inativar(List<UUID> produtosId) {
        produtosId.forEach(this::inativar);
    }

}
