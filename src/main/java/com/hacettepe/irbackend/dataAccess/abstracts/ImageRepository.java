package com.hacettepe.irbackend.dataAccess.abstracts;
import com.hacettepe.irbackend.entities.concretes.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
}