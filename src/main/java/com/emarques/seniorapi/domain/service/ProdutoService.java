package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.exception.EntidadeEmUsoException;
import com.emarques.seniorapi.domain.exception.ProdutoNaoEncontradoException;
import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private static final String MSG_PRODUTO_EM_USO
            = "O Produto %d não pode ser removida, pois está em uso";

    private ProdutoRepository produtoRepository;

    /*----- CRUD -------*/

    @Transactional
    public List<Produto> listar(){
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }

    @Transactional
    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizar(Long produtoId, Produto produto){
        Produto produtoAtual = buscarOuFalhar(produtoId);

        BeanUtils.copyProperties(produto,produtoAtual, "id");

        return produtoRepository.save(produtoAtual);
    }

    @Transactional
    public void remover(Long produtoId){
        try {
        produtoRepository.deleteById(produtoId);
        produtoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException(produtoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, produtoId));
        }
    }

    /*----- NEGOCIO -------*/

    @Transactional
    public void ativar(Long produtoId) {
        Produto produtoAtual = buscarOuFalhar(produtoId);
        produtoAtual.ativar();
    }

    @Transactional
    public void inativar(Long produtoId) {
        Produto produtoAtual = buscarOuFalhar(produtoId);

        produtoAtual.inativar();
    }

    @Transactional
    public void ativar(List<Long> produtosId) {
        produtosId.forEach(this::ativar);
    }

    @Transactional
    public void inativar(List<Long> produtosId) {
        produtosId.forEach(this::inativar);
    }

}
