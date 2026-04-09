package com.aplicacionestudiosmedicos.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArchivoStorageService {

    StoredFile uploadImage(MultipartFile file, String folder) throws IOException;

    StoredFile uploadPdf(byte[] content, String originalFilename, String folder) throws IOException;
}