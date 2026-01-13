package com.hh99.ecommerce.order.infra.event;

import com.hh99.ecommerce.common.kafka.KafkaMessageProducer;
import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final KafkaMessageProducer kafkaMessageProducer;
    private final com.hh99.ecommerce.order.domain.OrderEventService orderEventService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreatedEvent(OrderOutbox orderOutbox) {
        log.info("Sending message to Kafka for order: {}", orderOutbox.getOrderId());
        try {
            kafkaMessageProducer.send("order-create",
                String.valueOf(orderOutbox.getOrderId()),
                orderOutbox.getPayloadAsObject());
            orderEventService.setPublished(orderOutbox.getOrderId());
        } catch (Exception e) {
            log.error("Failed to send message to Kafka for order: {}", orderOutbox.getOrderId(), e);
        }
    }
}
