package com.ravn.movies.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SigninRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
