package com.ravn.movies.service;

import com.ravn.movies.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface IJwtService {
    String getEmailFromToken(String token);

    String generateToken(User user);
    String generateToken(Map<String, Object> claims, User user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
