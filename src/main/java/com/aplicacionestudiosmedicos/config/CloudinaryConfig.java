package com.aplicacionestudiosmedicos.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(@Value("${CLOUDINARY_URL:}") String cloudinaryUrl) {
        if (cloudinaryUrl == null || cloudinaryUrl.isBlank()) {
            throw new IllegalStateException("CLOUDINARY_URL es obligatorio en el perfil prod");
        }
        return new Cloudinary(cloudinaryUrl);
    }
}