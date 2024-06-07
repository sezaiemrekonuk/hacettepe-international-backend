package com.hacettepe.irbackend.business.abstracts;

import com.hacettepe.irbackend.business.requests.CreateAnnouncementRequest;
import com.hacettepe.irbackend.business.requests.UpdateAnnouncementRequest;
import com.hacettepe.irbackend.business.responses.GetAllAnnouncementsResponse;
import com.hacettepe.irbackend.business.responses.GetAnnouncementResponse;

import java.util.List;

public interface AnnouncementService {
    List<GetAllAnnouncementsResponse> getAll();
    void add(CreateAnnouncementRequest request);
    GetAnnouncementResponse getAnnouncementById(int id);
    void update(UpdateAnnouncementRequest request);
    void delete(int id);
}
