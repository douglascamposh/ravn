package com.ravn.movies.service;

import com.ravn.movies.domain.User;
import com.ravn.movies.dto.JwtAuthResponse;
import com.ravn.movies.dto.SigninRequest;
import com.ravn.movies.dto.SignupRequest;

public interface IUserService {

    User findByEmail(String email);
    JwtAuthResponse signUp(SignupRequest registerRequest);
    JwtAuthResponse signUpAdmin(SignupRequest registerRequest);
    JwtAuthResponse signIn(SigninRequest signinRequest);
}
