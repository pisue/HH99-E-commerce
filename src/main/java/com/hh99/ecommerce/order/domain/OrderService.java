package com.hh99.ecommerce.order.domain;

import com.hh99.ecommerce.order.domain.dto.CreateOrderDto;
import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import com.hh99.ecommerce.order.domain.dto.OrderItemDomain;
import com.hh99.ecommerce.order.infra.entity.Order;
import com.hh99.ecommerce.order.infra.entity.OrderItem;
import com.hh99.ecommerce.order.infra.repository.OrderItemRepository;
import com.hh99.ecommerce.order.infra.repository.OrderRepository;
import com.hh99.ecommerce.order.interfaces.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void createOrderItem(Long id, CreateOrderDto dto) {
        orderItemRepository.save(
                OrderItem.builder()
                        .orderId(id)
                        .productId(dto.getProductId())
                        .quantity(dto.getQuantity())
                        .itemPrice(dto.getPrice())
                        .build()
        );
    }

    public List<OrderDomain> getOrders(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(Order::toDomain).collect(Collectors.toList());
    }

    public List<OrderItemDomain> getOrderItems(Long id) {
        return orderItemRepository.findAllById(id).stream()
                .map(OrderItem::toDomain).collect(Collectors.toList());
    }

    public OrderDomain getOrder(Long userId, Long orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId).toDomain();
    }
}
