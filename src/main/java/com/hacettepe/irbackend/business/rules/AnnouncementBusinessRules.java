package com.hacettepe.irbackend.business.rules;

import com.hacettepe.irbackend.dataAccess.abstracts.AnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AnnouncementBusinessRules {
    private AnnouncementRepository announcementRepository;

    public boolean isTitleValid(String title) {
        return !title.isEmpty() && title.length() <= 100;
    }

}
