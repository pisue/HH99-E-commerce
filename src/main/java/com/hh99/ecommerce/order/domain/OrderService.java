package com.hh99.ecommerce.order.domain;

import com.hh99.ecommerce.order.domain.dto.CreateOrderDto;
import com.hh99.ecommerce.order.infra.entity.Order;
import com.hh99.ecommerce.order.infra.entity.OrderItem;
import com.hh99.ecommerce.order.infra.repository.OrderItemRepository;
import com.hh99.ecommerce.order.infra.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    @Transactional
    public void createOrder(CreateOrderDto dto) {
        // 주문 생성
        Order order = Order.builder()
                .userId(dto.getUserId())
                .orderDate(LocalDateTime.now())
                .totalPrice(dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())))
                .build();

        Order savedOrder = orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .orderId(savedOrder.getId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .itemPrice(dto.getPrice())
                .build();

        orderItemRepository.save(orderItem);
    }
}
