package com.hacettepe.irbackend.business.concretes;
import com.hacettepe.irbackend.core.utilities.image.ImageUtils;
import com.hacettepe.irbackend.dataAccess.abstracts.ImageRepository;
import com.hacettepe.irbackend.entities.concretes.Image;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageManager {

    private final ImageRepository imageRepository;

    private final Logger logger = LoggerFactory.getLogger(ImageManager.class);

    public String uploadImage(MultipartFile imageFile) throws IOException {
        var imageToSave = Image.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();
        imageRepository.save(imageToSave);
        return "file uploaded successfully : " + imageFile.getOriginalFilename();
    }

    public byte[] downloadImage(String imageName) {
        Optional<Image> dbImage = imageRepository.findByName(imageName);

        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                logger.error("Error occurred while decompressing image data", exception);
            }
            return null;
        }).orElse(null);
    }
}