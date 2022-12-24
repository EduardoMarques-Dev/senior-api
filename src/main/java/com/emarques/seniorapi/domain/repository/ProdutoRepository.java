package com.emarques.seniorapi.domain.repository;

import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.infrastructure.repository.CustomJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {
}
