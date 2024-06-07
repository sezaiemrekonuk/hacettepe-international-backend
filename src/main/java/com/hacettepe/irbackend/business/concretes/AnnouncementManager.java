package com.hacettepe.irbackend.business.concretes;

import com.hacettepe.irbackend.business.abstracts.AnnouncementService;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementManager implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final ModelMapperService modelMapperService;
    private final AnnouncementBusinessRules announcementBusinessRules;

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
        return this.modelMapperService
                .forResponse()
                .map(announcementRepository.findById(id).orElse(null), GetAnnouncementResponse.class);
    }

    @Override
    public void update(UpdateAnnouncementRequest request) {
        Announcement announcement = this.modelMapperService
                .forRequests()
                .map(request, Announcement.class);
        this.announcementRepository.save(announcement);
    }

    @Override
    public void delete(int id) {
        this.announcementRepository.deleteById(id);
    }
}
