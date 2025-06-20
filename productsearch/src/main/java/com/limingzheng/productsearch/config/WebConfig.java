package com.limingzheng.productsearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration Class
 * 
 * This class configures Spring MVC's static resource handling, specifically for Swagger UI.
 * It ensures that Swagger UI's static resources (HTML, CSS, JS files) can be properly served
 * when accessed through the web browser.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure Swagger UI static resources
        // Maps URL pattern "/swagger-ui/**" to the actual location of Swagger UI files in the JAR
        // The files are located in the webjars directory within the classpath
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/5.11.8/");
        
        // Configure general webjars resources
        // This ensures that all webjars (including Swagger UI dependencies) are accessible
        // Webjars are JAR files that contain web resources like JavaScript libraries
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
} 