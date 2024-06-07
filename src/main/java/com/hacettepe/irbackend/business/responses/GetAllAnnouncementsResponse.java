package com.hacettepe.irbackend.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAnnouncementsResponse {
    private int id;
    private String title;
    private String author;
    private String imagePath;
}
