package com.hacettepe.irbackend.business.concretes;

import com.hacettepe.irbackend.business.abstracts.AnnouncementService;
import com.hacettepe.irbackend.business.abstracts.StorageService;
import com.hacettepe.irbackend.business.requests.CreateAnnouncementRequest;
import com.hacettepe.irbackend.business.requests.UpdateAnnouncementRequest;
import com.hacettepe.irbackend.business.responses.GetAllAnnouncementsResponse;
import com.hacettepe.irbackend.business.responses.GetAnnouncementResponse;
import com.hacettepe.irbackend.business.rules.AnnouncementBusinessRules;
import com.hacettepe.irbackend.core.utilities.mappers.ModelMapperService;
import com.hacettepe.irbackend.dataAccess.abstracts.AnnouncementRepository;
import com.hacettepe.irbackend.entities.concretes.Announcement;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementManager implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final ModelMapperService modelMapperService;
    private final AnnouncementBusinessRules announcementBusinessRules;
    private final StorageService storageService;
    private final Logger logger = LoggerFactory.getLogger(AnnouncementManager.class);

    @Override
    public List<GetAllAnnouncementsResponse> getAll() {
        List<Announcement> announcements = announcementRepository.findAll();

        return announcements.stream()
                .map(announcement -> this.modelMapperService
                        .forResponse()
                        .map(announcement, GetAllAnnouncementsResponse.class))
                .toList();
    }

    @Override
    public void add(CreateAnnouncementRequest request) {
        this.announcementBusinessRules.isTitleValid(request.getTitle());
        Announcement announcement = this.modelMapperService
                .forRequests()
                .map(request, Announcement.class);
        this.announcementRepository.save(announcement);
    }

    @Override
    public GetAnnouncementResponse getAnnouncementById(int id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isEmpty()) {
            logger.error("Announcement not found with id: {}", id);
        } else {
            logger.info("Announcement found with id: {}, title is {}", id, announcement.get().getTitle());
        }

        return this.modelMapperService
                .forResponse()
                .map(announcement.orElse(null), GetAnnouncementResponse.class);
    }

    @Override
    public String update(UpdateAnnouncementRequest request) {
        logger.info("Updating announcement with id: {}", request.getAnnouncementId());
        Optional<Announcement> announcementOptional = this.announcementRepository.findById(request.getAnnouncementId());

        if (announcementOptional.isPresent()) {
            Announcement existingAnnouncement = announcementOptional.get();
            boolean imageChanged = false;

            if (!existingAnnouncement.getImagePath().equals(request.getImagePath())) {
                // delete old image from images directory
                this.storageService.deleteImage(existingAnnouncement.getImagePath());
                imageChanged = true;
            }

            Announcement updatedAnnouncement = this.modelMapperService
                    .forRequests()
                    .map(request, Announcement.class);
            this.announcementRepository.save(updatedAnnouncement);

            if (imageChanged) {
                return "image";
            }
            return "no image";
        } else {
            logger.error("Can't update announcement with id: {}", request.getAnnouncementId());
            throw new IllegalArgumentException("Announcement not found with id: " + request.getAnnouncementId());
        }
    }


    @Override
    public void delete(int id) {
        Announcement announcement = this.announcementRepository.findById(id).orElse(null);
        if (announcement == null) {
            throw new IllegalArgumentException("Announcement not found with id: " + id);
        }
        // delete announcements image from images directory
        String imagePath = announcement.getImagePath();
        this.storageService.deleteImage(imagePath);


        this.announcementRepository.deleteById(id);

    }
}
