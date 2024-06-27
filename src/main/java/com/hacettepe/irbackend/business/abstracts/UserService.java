package com.hacettepe.irbackend.business.abstracts;

import com.hacettepe.irbackend.business.requests.CreateUserRequest;
import com.hacettepe.irbackend.business.requests.UpdateUserRequest;
import com.hacettepe.irbackend.business.responses.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);
    boolean deleteUser(Long id);

}
