package com.aplicacionestudiosmedicos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("!prod")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Sirve las imágenes subidas
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        //  Sirve los PDF generados
        registry.addResourceHandler("/pdfs/**")
                .addResourceLocations("file:pdfs/");
    }
}
