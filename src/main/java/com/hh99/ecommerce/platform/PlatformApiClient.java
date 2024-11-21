package com.hh99.ecommerce.platform;

import com.hh99.ecommerce.order.domain.dto.OrderOutboxPayload;

public interface PlatformApiClient {
    void sendMessageOrderInfo(OrderOutboxPayload payload);
}
