package com.hh99.ecommerce.common.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String key, Object message) {
        try {
            log.info("Sending message to topic {}, key: {}, message: {}", topic, key, message);
            kafkaTemplate.send(topic, key, message);
        } catch (Exception e) {
            log.error("Error sending message to topic {}: {}", topic, e.getMessage());
            throw e;
        }
    }
}
