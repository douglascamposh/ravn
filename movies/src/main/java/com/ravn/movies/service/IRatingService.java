package com.ravn.movies.service;

import com.ravn.movies.domain.Rating;
import com.ravn.movies.dto.MovieRatingDTO;
import com.ravn.movies.dto.RatingDTO;

import java.util.List;

public interface IRatingService {
    void rateMovie(RatingDTO ratingDTO, String email);
    void removeMovieRating(Long movieId, String email);
    List<MovieRatingDTO> getMyMovieRatings(String email);
}
