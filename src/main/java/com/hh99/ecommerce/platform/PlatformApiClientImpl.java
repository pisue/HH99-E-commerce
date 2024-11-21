package com.hh99.ecommerce.platform;

import com.hh99.ecommerce.order.domain.dto.OrderOutboxPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
public class PlatformApiClientImpl implements PlatformApiClient {
    private final String platformApiUrl;
    private final RestTemplate restTemplate;

    @Override
    public void sendMessageOrderInfo(OrderOutboxPayload payload) {
        PlatformMessage message = createPlatformMessage(payload);
        log.info("\nSending platform message to platform api \nurl: {}\nmessage: {}", platformApiUrl, message.getText());
    }

    private PlatformMessage createPlatformMessage(OrderOutboxPayload payload) {
        return new PlatformMessage(
                String.format("Order received: %s\nStatus: %s\nTotal Items Count: %s",
                        payload.getOrderId(),
                        payload.getUserId(),
                        payload.getOrderItems().size()
                )
        );
    }

    private static class PlatformMessage {
        private final String text;

        public PlatformMessage(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
