package com.ravn.movies.service.impl;

import com.ravn.movies.domain.User;
import com.ravn.movies.dto.JwtAuthResponse;
import com.ravn.movies.dto.SigninRequest;
import com.ravn.movies.dto.SignupRequest;
import com.ravn.movies.dto.enums.Role;
import com.ravn.movies.error.DataNotFoundException;
import com.ravn.movies.error.InternalErrorException;
import com.ravn.movies.repository.IUserRepository;
import com.ravn.movies.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException("User with email: " + email + " already exists"));
    }

    @Override
    public JwtAuthResponse signUp(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            logger.error("User with email: " + signupRequest.getEmail() + " already exists");
            throw new IllegalArgumentException("User with email: " + signupRequest.getEmail() + " already exists");
        }
        User user = modelMapper.map(signupRequest, User.class);
        user.setRole(Role.USER.toString());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthResponse signUpAdmin(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            logger.error("User with email: " + signupRequest.getEmail() + " already exists");
            throw new IllegalArgumentException("User with email: " + signupRequest.getEmail() + " already exists");
        }
        User user = modelMapper.map(signupRequest, User.class);
        user.setId(null);
        user.setRole(Role.ADMIN.toString());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthResponse signIn(SigninRequest signinRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword())
        );
        User user = userRepository.findUserByEmail(signinRequest.getEmail()).orElseThrow(() -> new InternalErrorException("User not found"));
        String jwt = jwtService.generateToken(user);
        logger.info("User signed in successfully");
        return JwtAuthResponse.builder().token(jwt).build();
    }
}
