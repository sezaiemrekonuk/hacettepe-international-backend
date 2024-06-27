package com.hacettepe.irbackend.webApi.controllers;

import com.hacettepe.irbackend.business.requests.JwtRequest;
import com.hacettepe.irbackend.business.requests.JwtValidationRequest;
import com.hacettepe.irbackend.business.responses.JwtResponse;
import com.hacettepe.irbackend.business.concretes.JwtUserDetailsManager;
import com.hacettepe.irbackend.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class JwtAuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsManager userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        logger.info("Received authentication request for user: {}", authenticationRequest.getUsername());

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        logger.info("Loaded user details for user: {}", authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        logger.info("Generated token for user: {}", authenticationRequest.getUsername());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/validateToken")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> validateToken(@RequestBody JwtValidationRequest jwtRequest) {
        logger.info("Received token validation request for user: {}", jwtRequest.getUsername());
        final String token = jwtRequest.getToken();
        final String username = jwtRequest.getUsername();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        logger.info("Loaded user details for user: {}", username);
        if (jwtTokenUtil.validateToken(token, userDetails)) {
            logger.info("Token is valid for user: {}", username);
            return ResponseEntity.ok().build();
        } else {
            logger.error("Token is not valid for user: {}", username);
            return ResponseEntity.status(401).build();
        }
    }




    private void authenticate(String username, String password) throws Exception {
        try {
            logger.info("Attempting to authenticate user: {}", username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            logger.info("User authenticated successfully: {}", username);
        } catch (DisabledException e) {
            logger.error("User is disabled: {}", username, e);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials for user: {}", username, e);
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", username, e);
            throw e;
        }
    }

}
