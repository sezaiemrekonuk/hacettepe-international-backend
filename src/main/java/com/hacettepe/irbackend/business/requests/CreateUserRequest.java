package com.hacettepe.irbackend.business.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;

    // Getters and Setters
}
