package com.ravn.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long id;

    private String name;

    private String synopsis;

    private Long year;

    private Long categoryId;

    private String imagePoster;
    private String categoryName;
    private Long userId;
}
