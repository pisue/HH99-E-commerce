package com.hh99.ecommerce.order.infra.repository;

import com.hh99.ecommerce.order.infra.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
