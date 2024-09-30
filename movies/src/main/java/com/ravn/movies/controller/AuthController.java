package com.ravn.movies.controller;

import com.ravn.movies.dto.JwtAuthResponse;
import com.ravn.movies.dto.SigninRequest;
import com.ravn.movies.dto.SignupRequest;
import com.ravn.movies.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(AuthController.BASE_CTRL_URL)
public class AuthController {

    public static final String BASE_CTRL_URL = "/api/v1/auth";
    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public JwtAuthResponse createUser(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.signUp(signupRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registerAdmin")
    public JwtAuthResponse createUserAdmin(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.signUpAdmin(signupRequest);
    }

    @PostMapping("/login")
    public JwtAuthResponse login(@Valid @RequestBody SigninRequest signinRequest) {
       return userService.signIn(signinRequest);
    }
}
