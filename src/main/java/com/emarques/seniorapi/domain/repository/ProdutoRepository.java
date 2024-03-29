package com.emarques.seniorapi.domain.repository;

import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.infrastructure.repository.CustomJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, UUID> {

    Page<Produto> findByNomeContainingIgnoreCase(String nomeProduto, Pageable pageable);

}
