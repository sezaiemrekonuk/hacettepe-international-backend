package com.hacettepe.irbackend.business.concretes;

import com.hacettepe.irbackend.business.abstracts.UserService;
import com.hacettepe.irbackend.business.requests.CreateUserRequest;
import com.hacettepe.irbackend.business.requests.UpdateUserRequest;
import com.hacettepe.irbackend.business.responses.UserResponse;
import com.hacettepe.irbackend.dataAccess.abstracts.UserRepository;
import com.hacettepe.irbackend.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getUsername());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user.getId(), user.getUsername());
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updateUserRequest.getUsername());
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getUsername());
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (RuntimeException e) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }
}
