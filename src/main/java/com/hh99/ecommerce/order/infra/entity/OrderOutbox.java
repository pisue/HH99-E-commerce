package com.hh99.ecommerce.order.infra.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hh99.ecommerce.order.domain.dto.OrderItemPayload;
import com.hh99.ecommerce.order.domain.dto.OrderOutboxPayload;
import com.hh99.ecommerce.order.domain.enums.OutboxStatus;
import com.hh99.ecommerce.order.interfaces.request.OrderCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxStatus status;

    @Column(columnDefinition = "json", nullable = false)
    private String payload;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime processedAt;

    @Builder
    public OrderOutbox(Long orderId, OutboxStatus status, OrderOutboxPayload payload) {
        this.orderId = orderId;
        this.status = status;
        this.payload = convertPayloadToJson(payload);
        this.createdAt = LocalDateTime.now();
    }

    // 정적 팩토리 메서드 추가
    public static OrderOutbox createFrom(Order order, List<OrderCreateRequest> requests) {
        OrderOutboxPayload payload = createOrderPayload(order, requests);

        return OrderOutbox.builder()
                .orderId(order.getId())
                .status(OutboxStatus.INIT)
                .payload(payload)
                .build();
    }

    public static OrderOutboxPayload createOrderPayload(Order order, List<OrderCreateRequest> requests) {
        List<OrderItemPayload> items = requests.stream()
                .map(request -> OrderItemPayload.builder()
                        .productId(request.getProductId())
                        .quantity(request.getQuantity())
                        .build())
                .toList();

        return OrderOutboxPayload.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .orderItems(items)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void updateStatus(OutboxStatus status) {
        this.status = status;
        this.processedAt = LocalDateTime.now();
    }

    private String convertPayloadToJson(OrderOutboxPayload payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON으로 변환이 실패하였습니다.", e);
        }
    }

    public OrderOutboxPayload getPayloadAsObject() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());  // LocalDateTime 처리를 위해 필요
            return objectMapper.readValue(this.payload, OrderOutboxPayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON을 객체로 변환하는데 실패하였습니다.", e);
        }
    }

    public void published() {
        this.status = OutboxStatus.PUBLISHED;
        this.processedAt = LocalDateTime.now();
    }
}
