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
    @Value("${app.upload.dir}")
    String uploadDir;

    public String uploadPfp(MultipartFile file , String subDirectory)throws IOException {
        Path uploadPath = Paths.get(uploadDir , subDirectory);
        Files.createDirectories(uploadPath);

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path resolvePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream() , resolvePath , StandardCopyOption.REPLACE_EXISTING);

        return "/" + uploadPath + "/" + subDirectory + "/" + filename;
    }
    public void deleteImage(String imageUrl) throws IOException{
        if(imageUrl == null || imageUrl.isBlank()){
            return;
        }
        String relativePath = imageUrl.startsWith("/")
                ? imageUrl.substring(1)
                : imageUrl;

        Path path = Paths.get(relativePath);
        Files.deleteIfExists(path);
    }
}