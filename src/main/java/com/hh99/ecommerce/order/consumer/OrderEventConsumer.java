package com.hh99.ecommerce.order.consumer;

import com.hh99.ecommerce.order.domain.dto.OrderOutboxPayload;
import com.hh99.ecommerce.order.domain.enums.OutboxStatus;
import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import com.hh99.ecommerce.order.infra.repository.OrderOutboxRepository;
import com.hh99.ecommerce.platform.PlatformApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final OrderOutboxRepository orderOutboxRepository;

    @KafkaListener(topics = "order-create")
    public void handleOrderCreated(OrderOutboxPayload payload) {
        log.info("Received order created event: {}", payload);
        
        OrderOutbox outbox = orderOutboxRepository.findByOrderId(payload.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found: " + payload.getOrderId()));
        
        outbox.updateStatus(OutboxStatus.PUBLISHED     );
        orderOutboxRepository.save(outbox);
    }
}
