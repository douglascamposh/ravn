package com.ravn.movies.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class RatingDTO {
    private Long movieId;
    @Min(1)
    @Max(5)
    private int rating;
}
