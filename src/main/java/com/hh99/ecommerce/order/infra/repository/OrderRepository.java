package com.hh99.ecommerce.order.infra.repository;

import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import com.hh99.ecommerce.order.infra.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);

    Order findByIdAndUserId(Long userId, Long orderId);
}
