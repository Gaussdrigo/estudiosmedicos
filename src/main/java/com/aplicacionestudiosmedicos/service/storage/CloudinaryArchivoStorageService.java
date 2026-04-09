package com.aplicacionestudiosmedicos.service.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Profile("prod")
public class CloudinaryArchivoStorageService implements ArchivoStorageService {

    private final Cloudinary cloudinary;

    public CloudinaryArchivoStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public StoredFile uploadImage(MultipartFile file, String folder) throws IOException {
        return upload(file.getBytes(), file.getOriginalFilename(), folder, "image");
    }

    @Override
    public StoredFile uploadPdf(byte[] content, String originalFilename, String folder) throws IOException {
        return upload(content, originalFilename, folder, "raw");
    }

    private StoredFile upload(byte[] content, String originalFilename, String folder, String resourceType) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(content, ObjectUtils.asMap(
                "folder", folder,
                "resource_type", resourceType,
                "use_filename", true,
            "unique_filename", true,
            "overwrite", false
        ));

        String publicId = (String) result.get("public_id");
        String secureUrl = (String) result.get("secure_url");
        String returnedFilename = (String) result.get("original_filename");

        return new StoredFile(
                publicId,
                secureUrl,
                returnedFilename != null ? returnedFilename : originalFilename,
                resourceType
        );
    }
}