package com.emarques.seniorapi.domain.service;

import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    @Transactional
    public List<Produto> listar(){
        System.out.println("Listando o produto");
        return produtoRepository.findAll();
    }
}
