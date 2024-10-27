package com.hh99.ecommerce.order.domain.repository;

import com.hh99.ecommerce.order.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
