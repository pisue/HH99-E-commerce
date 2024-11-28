package com.hh99.ecommerce.order.domain;

import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import com.hh99.ecommerce.order.infra.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventService {
    private final OrderOutboxRepository orderOutboxRepository;
    private final ApplicationEventPublisher eventPublisher;


    public OrderOutbox save(OrderOutbox orderOutbox) {
        return orderOutboxRepository.save(orderOutbox);
    }

    @Transactional
    public void saveAndPublishEvent(OrderOutbox orderOutbox) {
        log.info("Publishing event for orderId: {}", orderOutbox.getOrderId());
        eventPublisher.publishEvent(orderOutbox);  // 먼저 이벤트 발행

        log.info("Saving outbox for orderId: {}", orderOutbox.getOrderId());
        orderOutboxRepository.save(orderOutbox);   // 그 다음 저장
    }
}
