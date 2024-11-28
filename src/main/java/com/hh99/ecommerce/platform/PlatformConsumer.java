package com.hh99.ecommerce.platform;

import com.hh99.ecommerce.order.domain.dto.OrderOutboxPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformConsumer {
    private final PlatformApiClient platformApiClient;

    @KafkaListener(
            topics = "order-create",
            groupId = "group-02"
    )
    public void handleOrderCreated(OrderOutboxPayload payload) {
        log.info("Received Platform order created event: {}", payload);
        platformApiClient.sendMessageOrderInfo(payload);
        log.info("Successfully published message for order: {}", payload.getOrderId());
    }
}
