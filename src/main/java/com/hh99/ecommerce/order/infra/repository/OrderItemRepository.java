package com.hh99.ecommerce.order.infra.repository;

import com.hh99.ecommerce.order.infra.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllById(Long id);
}
