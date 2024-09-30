package com.ravn.movies.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MovieFormDTO {

    @NotNull
    private String name;

    @NotNull
    private String synopsis;

    @NotNull
    private Long year;

    private Long categoryId;

    private String imagePoster;

}
