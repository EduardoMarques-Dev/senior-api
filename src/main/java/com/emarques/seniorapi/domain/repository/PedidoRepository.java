package com.emarques.seniorapi.domain.repository;

import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.infrastructure.repository.CustomJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
    Optional<Pedido> findByCodigo(String codigo);
    void deleteByCodigo(String codigo);
}
