package com.hacettepe.irbackend.business.concretes;

import com.hacettepe.irbackend.business.abstracts.StorageService;
import com.hacettepe.irbackend.dataAccess.abstracts.StorageRepository;
import com.hacettepe.irbackend.entities.concretes.FileData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StorageManager implements StorageService {
    private StorageRepository storageRepository;

    // current projects images directory
    private final String FILE_PATH = Paths.get("").toAbsolutePath() + "/src/main/resources/static/images/";
    private final String STATIC_PATH = Paths.get("").toAbsolutePath() + "/src/main/resources/static/";

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String filePath = FILE_PATH + file.getOriginalFilename();
        String filePathFromStatic = filePath.split("static")[1];

        if (storageRepository.findByFilePath(filePathFromStatic) == null) {
            FileData newFile = storageRepository.save(
                    FileData.builder()
                            .name(file.getOriginalFilename())
                            .filePath(filePathFromStatic)
                            .type(file.getContentType())
                            .build()
            );
        }

        if (file != null) {
            file.transferTo(Paths.get(filePath));
            return filePathFromStatic;
        }

        return "Error occurred while uploading image";
    }

    @Override
    public void deleteImage(String imagePath) {
        try {
            Files.deleteIfExists(Paths.get(STATIC_PATH + imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        storageRepository.delete(storageRepository.findByFilePath(imagePath));
    }

    @Override
    public String getFilePath(String name) {
        return storageRepository.findByName(name).getFilePath();
    }

    @Override
    public List<FileData> getAllFilesWithType(String type) {
        return storageRepository.findAllByType(type);
    }

    @Override
    public String uploadToGallery(MultipartFile file) throws IOException {
        String filePath = FILE_PATH + "gallery/" +file.getOriginalFilename();
        String filePathFromStatic = filePath.split("static")[1];

        if (storageRepository.findByFilePath(filePathFromStatic) == null) {
            FileData newFile = storageRepository.save(
                    FileData.builder()
                            .name(file.getOriginalFilename())
                            .filePath(filePathFromStatic)
                            .type("gallery"+file.getContentType())
                            .build()
            );
        }

        if (file != null) {
            file.transferTo(Paths.get(filePath));
            return filePathFromStatic;
        }

        return "Error occurred while uploading image";
    }
}
