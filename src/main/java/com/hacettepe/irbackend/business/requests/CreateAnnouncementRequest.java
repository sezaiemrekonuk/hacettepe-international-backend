package com.hacettepe.irbackend.business.requests;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnnouncementRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 10000)
    private String content;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String author;

    @Size(min = 1, max = 100)
    private String imagePath;

}
