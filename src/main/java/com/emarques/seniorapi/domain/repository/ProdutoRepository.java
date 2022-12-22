package com.emarques.seniorapi.domain.repository;

import com.emarques.seniorapi.domain.model.Produto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {
}
