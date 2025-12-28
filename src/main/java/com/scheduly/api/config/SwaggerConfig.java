package com.scheduly.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Value("${spring.application.version:1.0.0}")
        private String appVersion;

        @Value("${spring.application.name:scheduly-api}")
        private String applicationName;

        @Value("${spring.application.description:Scheduly API Documentation}")
        private String appDescription;

        @Bean
        public OpenAPI springShopOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title(applicationName)
                                                .description(appDescription)
                                                .version(appVersion));
        }
}
