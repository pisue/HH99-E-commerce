package com.hh99.ecommerce.order.infra.scheduler;

import com.hh99.ecommerce.common.kafka.KafkaMessageProducer;
import com.hh99.ecommerce.order.domain.enums.OutboxStatus;
import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import com.hh99.ecommerce.order.infra.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderOutboxScheduler {
    private final KafkaMessageProducer kafkaMessageProducer;
    private final OrderOutboxRepository outboxRepository;

    @Scheduled(fixedRate = 5000)
    public void processUnsentMessages() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);

        outboxRepository.findAllByStatusAndCreatedAtBefore(OutboxStatus.INIT, fiveMinutesAgo)
                .forEach(outbox -> {
                    kafkaMessageProducer.send("order-create", 
                        String.valueOf(outbox.getOrderId()), 
                        outbox.getPayloadAsObject());
                    log.info("Successfully republished delayed message for order: {}", 
                        outbox.getOrderId());
                });
    }
} 