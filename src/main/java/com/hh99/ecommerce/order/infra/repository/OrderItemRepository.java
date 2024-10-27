package com.hh99.ecommerce.order.infra.repository;

import com.hh99.ecommerce.order.infra.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
