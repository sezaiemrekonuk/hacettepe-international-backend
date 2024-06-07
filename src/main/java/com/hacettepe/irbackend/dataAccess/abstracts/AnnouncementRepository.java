package com.hacettepe.irbackend.dataAccess.abstracts;

import com.hacettepe.irbackend.entities.concretes.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    boolean existsByTitle(String title);
}
