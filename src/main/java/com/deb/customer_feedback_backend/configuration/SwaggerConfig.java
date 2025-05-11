package com.deb.customer_feedback_backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Customer Feedback SaaS API")
						.version("1.0")
						.contact(new Contact()
								.name("Ajoy Deb Nath")
								.email("ajoydeb.cuet@gmail.com")));
	}
}
