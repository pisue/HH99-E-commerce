package com.hh99.ecommerce.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String API_NAME = "E-Commerce API";
    private static final String API_VERSION = "1.0";
    private static final String API_DESCRIPTION = "E-Commerce 서비스 API";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION);

        return new OpenAPI()
                .info(info);
    }

}
