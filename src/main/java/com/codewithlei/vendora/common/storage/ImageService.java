package com.codewithlei.vendora.common.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    private final Path uploadRoot;

    public ImageService(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public String uploadPfp(MultipartFile file, String dir) throws IOException {
        String extension = getExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + extension;

        Path targetDir = uploadRoot.resolve(dir).normalize();
        if (!targetDir.startsWith(uploadRoot)) {
            throw new IllegalArgumentException("Invalid folder");
        }
        Files.createDirectories(targetDir);

        Files.copy(file.getInputStream(), targetDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + dir + "/" + fileName;
    }

    public void deleteImage(String url) {
        Path file = uploadRoot.resolve(url.replaceFirst("^/uploads/", "")).normalize();
        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
        }
    }

    private String getExtension(String originalName) {
        if (originalName == null || !originalName.contains(".")) {
            return "";
        }
        return originalName.substring(originalName.lastIndexOf('.')).toLowerCase();
    }
}