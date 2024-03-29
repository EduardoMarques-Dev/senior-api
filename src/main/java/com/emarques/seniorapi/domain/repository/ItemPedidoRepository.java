package com.emarques.seniorapi.domain.repository;

import com.emarques.seniorapi.domain.model.ItemPedido;
import com.emarques.seniorapi.infrastructure.repository.CustomJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemPedidoRepository extends CustomJpaRepository<ItemPedido, UUID> {
}
