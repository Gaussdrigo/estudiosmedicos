package com.aplicacionestudiosmedicos.service.storage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;

@Service
@Profile("!prod")
public class LocalArchivoStorageService implements ArchivoStorageService {

    private static final Path UPLOAD_DIR = Paths.get("uploads");
    private static final Path PDF_DIR = Paths.get("pdfs");

    @Override
    public StoredFile uploadImage(MultipartFile file, String folder) throws IOException {
        String originalFilename = sanitizeFilename(file.getOriginalFilename());
        String storedFilename = buildStoredFilename(originalFilename);
        Path targetFile = ensureDirectory(UPLOAD_DIR).resolve(storedFilename);
        Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

        return new StoredFile(
                targetFile.getFileName().toString(),
                "/uploads/" + targetFile.getFileName(),
                originalFilename,
                "image"
        );
    }

    @Override
    public StoredFile uploadPdf(byte[] content, String originalFilename, String folder) throws IOException {
        String sanitizedFilename = sanitizeFilename(originalFilename);
        String storedFilename = buildStoredFilename(sanitizedFilename);
        Path targetFile = ensureDirectory(PDF_DIR).resolve(storedFilename);
        Files.write(targetFile, content);

        return new StoredFile(
                targetFile.getFileName().toString(),
                "/pdfs/" + targetFile.getFileName(),
                sanitizedFilename,
                "raw"
        );
    }

    private Path ensureDirectory(Path path) throws IOException {
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        return path;
    }

    private String buildStoredFilename(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    private String sanitizeFilename(String filename) {
        if (filename == null || filename.isBlank()) {
            return "archivo";
        }
        String normalized = Paths.get(filename).getFileName().toString();
        return normalized.replaceAll("[^a-zA-Z0-9._-]", "_").toLowerCase(Locale.ROOT);
    }
}