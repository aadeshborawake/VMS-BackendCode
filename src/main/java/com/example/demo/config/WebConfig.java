package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Update the mapping to match your API endpoints
                .allowedOrigins("http://localhost:3000") // Update with your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);// Allow specific HTTP methods
        		
    }
}
