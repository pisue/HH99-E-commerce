package com.hh99.ecommerce.order.domain;

import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import com.hh99.ecommerce.order.infra.repository.OrderOutboxRepository;
import com.hh99.ecommerce.order.interfaces.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderEventService {
    private final OrderOutboxRepository orderOutboxRepository;

    public OrderOutbox save(OrderDomain orderDomain, List<OrderCreateRequest> requests) {
        return orderOutboxRepository.save(OrderOutbox.createFrom(orderDomain.toEntity(), requests));
    }
}
