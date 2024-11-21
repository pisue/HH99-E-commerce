package com.hh99.ecommerce.platform;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PlatformConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PlatformApiClient platformApiClient(RestTemplate restTemplate) {
        return new PlatformApiClientImpl("https://platform.com", restTemplate);
    }
}
