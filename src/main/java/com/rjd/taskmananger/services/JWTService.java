package com.rjd.taskmananger.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails, Integer userId);

    Integer extractUserId(String token);
}
