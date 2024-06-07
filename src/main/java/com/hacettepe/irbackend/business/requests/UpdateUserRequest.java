package com.hacettepe.irbackend.business.requests;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String password;
}
