package com.hh99.ecommerce.order.domain;

import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import com.hh99.ecommerce.order.infra.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventService {
    private final OrderOutboxRepository orderOutboxRepository;

    public OrderOutbox save(OrderOutbox orderOutbox) {
        return orderOutboxRepository.save(orderOutbox);
    }
}
