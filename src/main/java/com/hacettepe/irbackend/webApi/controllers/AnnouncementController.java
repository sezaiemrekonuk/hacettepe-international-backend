package com.hacettepe.irbackend.webApi.controllers;

import com.hacettepe.irbackend.business.abstracts.AnnouncementService;
import com.hacettepe.irbackend.business.requests.CreateAnnouncementRequest;
import com.hacettepe.irbackend.business.requests.UpdateAnnouncementRequest;
import com.hacettepe.irbackend.business.responses.GetAllAnnouncementsResponse;
import com.hacettepe.irbackend.business.responses.GetAnnouncementResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcements")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class AnnouncementController {
    private AnnouncementService announcementService;

    @GetMapping()
    public List<GetAllAnnouncementsResponse> getAll(){
        return announcementService.getAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody CreateAnnouncementRequest request){
        announcementService.add(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetAnnouncementResponse getAnnouncementById(@PathVariable int id){
        return announcementService.getAnnouncementById(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody UpdateAnnouncementRequest request){
        String state = announcementService.update(request);
        return state;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id){
        announcementService.delete(id);
    }

}
