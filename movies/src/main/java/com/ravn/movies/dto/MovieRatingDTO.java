package com.ravn.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRatingDTO {
    private Long id;
    private Long userId;
    private int rating;
    private MovieDTO movie;
}
