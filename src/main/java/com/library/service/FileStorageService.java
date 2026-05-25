package com.library.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "src/main/resources/static/images/";

    public String saveFile(MultipartFile file) {

        if (file.isEmpty()) {
            return null;
        }

        try {
            // Generate unique filename
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            File dest = new File(uploadDir + filename);

            // Create directory if not exists
            dest.getParentFile().mkdirs();

            file.transferTo(dest);

            // Return URL path for DB
            return "/images/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file");
        }
    }
}