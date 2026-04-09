package com.aplicacionestudiosmedicos.service.storage;

public record StoredFile(
        String publicId,
        String secureUrl,
        String originalFilename,
        String resourceType) {
}