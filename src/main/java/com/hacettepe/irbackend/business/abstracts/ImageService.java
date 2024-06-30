package com.hacettepe.irbackend.business.abstracts;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    byte[] downloadImage(String imageName);
    String uploadImage(MultipartFile imageFile);
}
