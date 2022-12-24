package com.emarques.seniorapi.domain.repository;

import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.infrastructure.repository.CustomJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, UUID> {
}
