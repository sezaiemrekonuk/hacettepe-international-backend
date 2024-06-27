package com.hacettepe.irbackend.dataAccess.abstracts;

import com.hacettepe.irbackend.entities.concretes.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageRepository extends JpaRepository<FileData, Integer> {
    List<FileData> findAllByType(String type);
    FileData findByName(String name);
    FileData findByFilePath(String imagePath);
}
