package com.deb.customer_feedback_backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppConfig {
	@Value("${static.resource.path}")
    private String staticResourcePath;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOriginPatterns("*")
						.allowedHeaders("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS")
						.allowCredentials(true);
			}
			
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/imgs/**")
                		.addResourceLocations(staticResourcePath + "/static/imgs/");
		        registry.addResourceHandler("/documents/**")
		                .addResourceLocations(staticResourcePath + "/static/documents/");
			}
		};
	}
	
	@Bean
    public AuditorAware<String> auditorAware() {
        return new CustomAuditAware();
    }
}
