package com.emarques.seniorapi.domain.repository;

import com.emarques.seniorapi.domain.model.Pedido;
import com.emarques.seniorapi.infrastructure.repository.CustomJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, UUID> {
}
