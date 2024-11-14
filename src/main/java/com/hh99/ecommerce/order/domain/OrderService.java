package com.hh99.ecommerce.order.domain;

import com.hh99.ecommerce.order.domain.dto.CreateOrderItemDto;
import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import com.hh99.ecommerce.order.domain.dto.OrderItemDomain;
import com.hh99.ecommerce.order.domain.exception.OrderNotFoundException;
import com.hh99.ecommerce.order.infra.entity.Order;
import com.hh99.ecommerce.order.infra.entity.OrderItem;
import com.hh99.ecommerce.order.infra.repository.OrderItemRepository;
import com.hh99.ecommerce.order.infra.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderDomain createOrder(Long userId, BigDecimal totalPrice) {
        return orderRepository.save(
                Order.builder()
                        .userId(userId)
                        .orderDate(LocalDateTime.now())
                        .totalPrice(totalPrice)
                        .build()
        ).toDomain();
    }

    public void createOrderItem(OrderItemDomain orderItemDomain) {
        orderItemRepository.save(orderItemDomain.generateOrderItem());
    }

    public List<OrderDomain> getOrders(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(Order::toDomain)
                .toList();
    }

    public List<OrderItemDomain> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId).stream()
                .map(OrderItem::toDomain)
                .toList();
    }

    public OrderDomain getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new)
                .toDomain();
    }
}
