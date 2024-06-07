package com.hacettepe.irbackend.business.abstracts;

import com.hacettepe.irbackend.entities.concretes.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageService {
    String uploadImage(MultipartFile file) throws IOException;
    void deleteImage(String imagePath);
    String getFilePath(String name);
    List<FileData> getAllFilesWithType(String type);
}
