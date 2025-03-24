package com.example.addressbook.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI addressBookOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Address Book API")
                        .description("API documentation for Address Book Application")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Samiksha")
                                .email("sam@example.com")
                                .url("https://yourwebsite.com")));
    }
}
