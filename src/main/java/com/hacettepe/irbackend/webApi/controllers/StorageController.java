package com.hacettepe.irbackend.webApi.controllers;

import com.hacettepe.irbackend.business.abstracts.StorageService;
import com.hacettepe.irbackend.business.abstracts.UserService;
import com.hacettepe.irbackend.entities.concretes.FileData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/storage/")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class StorageController {
    StorageService storageService;

    @PostMapping()
    public String uploadFile(@RequestParam("image") MultipartFile file) throws IOException {
        return storageService.uploadImage(file);
    }

    @GetMapping()
    public List<FileData> getAllFilesWithType(@RequestParam String type) {
        return storageService.getAllFilesWithType(type);
    }

    @DeleteMapping()
    public void deleteFile(@RequestParam String filePath) {
        storageService.deleteImage(filePath);
    }

    @GetMapping("/{name}")
    public String getFilePath(@PathVariable String name) {
        return storageService.getFilePath(name);
    }

}
