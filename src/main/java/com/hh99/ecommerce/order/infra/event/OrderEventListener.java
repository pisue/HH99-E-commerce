package com.hh99.ecommerce.order.infra.event;

import com.hh99.ecommerce.common.kafka.KafkaMessageProducer;
import com.hh99.ecommerce.order.domain.enums.OutboxStatus;
import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import com.hh99.ecommerce.order.infra.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
    private final KafkaMessageProducer kafkaMessageProducer;
    private final OrderOutboxRepository outboxRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreatedEvent(OrderOutbox outbox) {
        log.info("Sending message to Kafka for order: {}", outbox.getOrderId());
        kafkaMessageProducer.send("order-create", String.valueOf(outbox.getOrderId()), outbox);
    }

    @Scheduled(fixedRate = 5000)
    public void processCheck(){
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);

        outboxRepository.findAllByStatusAndCreatedAtBefore(OutboxStatus.INIT, fiveMinutesAgo)
                .forEach(outbox -> {
                    kafkaMessageProducer.send("order-create", String.valueOf(outbox.getOrderId()), outbox);
                    log.info("Successfully republished delayed message for order: {}", outbox.getOrderId());
                });
    }
}
