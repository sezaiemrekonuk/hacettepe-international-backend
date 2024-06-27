package com.hacettepe.irbackend.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAnnouncementRequest {
    private int announcementId;
    private String title;
    private String content;
    private String imagePath;
}
