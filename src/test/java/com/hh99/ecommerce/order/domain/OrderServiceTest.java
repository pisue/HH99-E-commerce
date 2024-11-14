package com.hh99.ecommerce.order.domain;

import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import com.hh99.ecommerce.order.domain.dto.OrderItemDomain;
import com.hh99.ecommerce.order.domain.enums.OrderStatus;
import com.hh99.ecommerce.order.domain.exception.OrderNotFoundException;
import com.hh99.ecommerce.order.infra.entity.Order;
import com.hh99.ecommerce.order.infra.entity.OrderItem;
import com.hh99.ecommerce.order.infra.repository.OrderItemRepository;
import com.hh99.ecommerce.order.infra.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("주문 정보 저장")
    void createOrder() {
        //Given
        Long userId = 1L;
        BigDecimal totalPrice = BigDecimal.valueOf(100000);

        Order oder = Order.builder()
                .id(1L)
                .userId(userId)
                .totalPrice(totalPrice)
                .orderDate(LocalDateTime.now())
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(oder);
        //When
        OrderDomain result = orderService.createOrder(userId, totalPrice);

        //Then
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(totalPrice, result.getTotalPrice());
        assertEquals(userId, result.getUserId());
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("주문 상품 저장")
    void createOrderItem() {
        //Given
        OrderItemDomain orderItemDomain = OrderItemDomain.builder()
                .id(1L)
                .orderId(1L)
                .productId(1L)
                .quantity(2)
                .itemPrice(new BigDecimal("100000"))
                .createdAt(LocalDateTime.now())
                .orderStatus(OrderStatus.COMPLETED)
                .build();

        //When
        orderService.createOrderItem(orderItemDomain);

        //Then
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    @DisplayName("주문목록 조회")
    void getOrders() {
        //Given
        Long userId = 1L;
        Order order = Order.builder()
                .id(1L)
                .userId(userId)
                .totalPrice(new BigDecimal("300000"))
                .orderDate(LocalDateTime.now())
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .userId(userId)
                .totalPrice(new BigDecimal("230000"))
                .orderDate(LocalDateTime.now())
                .build();

        List<Order> orders = List.of(order, order2);
        when(orderRepository.findAllByUserId(userId)).thenReturn(orders);

        //When
        List<OrderDomain> orderDomains = orderService.getOrders(userId);
        List<Order> result = orderDomains.stream()
                .map(OrderDomain::toEntity)
                .toList();

        //Then
        verify(orderRepository, times(1)).findAllByUserId(userId);
        assertEquals(orders.size(), result.size());
        assertEquals(orders.get(0).getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("주문 상품 목록 조회")
    void getOrderItemsByOrderId() {
        //Given
        Long orderId = 1L;
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .orderId(orderId)
                .productId(1L)
                .quantity(2)
                .itemPrice(new BigDecimal("100000"))
                .createdAt(LocalDateTime.now())
                .orderStatus(OrderStatus.COMPLETED)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .id(2L)
                .orderId(orderId)
                .productId(3L)
                .quantity(1)
                .itemPrice(new BigDecimal("9000"))
                .createdAt(LocalDateTime.now())
                .orderStatus(OrderStatus.COMPLETED)
                .build();
        List<OrderItem> orderItems = List.of(orderItem, orderItem2);
        when(orderItemRepository.findAllByOrderId(orderId)).thenReturn(orderItems);

        //When
        List<OrderItemDomain> orderItemDomains = orderService.getOrderItemsByOrderId(orderId);
        List<OrderItem> result = orderItemDomains.stream()
                .map(OrderItemDomain::toEntity)
                .toList();

        //Then
        verify(orderItemRepository, times(1)).findAllByOrderId(orderId);
        assertEquals(orderItems.size(), result.size());
        assertEquals(orderItems.get(0).getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("주문 상세 조회")
    void getOrder() {
        //Given
        Long orderId = 1L;
        Order order = Order.builder()
                .id(1L)
                .userId(3L)
                .totalPrice(new BigDecimal("300000"))
                .orderDate(LocalDateTime.now())
                .build();
        when(orderRepository.findById(orderId)).thenReturn(Optional.ofNullable(order));

        //When
        OrderDomain orderDomain = orderService.getOrder(orderId);
        Order result = orderDomain.toEntity();

        //Then
        verify(orderRepository, times(1)).findById(orderId);
        assertEquals(orderId, result.getId());
        assertEquals(Objects.requireNonNull(order).getId(), result.getId());
    }

    @Test
    @DisplayName("주문 정보 존재하지 않을 때")
    void getOrderNotFound() {
        //Given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        OrderNotFoundException exception = assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrder(anyLong())
        );

        assertEquals("주문정보가 존재하지 않습니다.", exception.getMessage());
        verify(orderRepository, times(1)).findById(anyLong());
    }
}