package com.ravn.movies.service.impl;

import com.ravn.movies.domain.Movie;
import com.ravn.movies.domain.Rating;
import com.ravn.movies.domain.User;
import com.ravn.movies.dto.MovieRatingDTO;
import com.ravn.movies.dto.RatingDTO;
import com.ravn.movies.error.DataNotFoundException;
import com.ravn.movies.repository.IMovieRepository;
import com.ravn.movies.repository.IRatingRepository;
import com.ravn.movies.service.IRatingService;
import com.ravn.movies.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements IRatingService {

    private final IRatingRepository ratingRepository;
    private final IMovieRepository movieRepository;
    private final IUserService userService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void rateMovie(RatingDTO ratingDTO, String email) {
        User user = userService.findByEmail(email);
        if (ratingRepository.existsByMovieIdAndUserId(ratingDTO.getMovieId(), user.getId())) {
            logger.error("Movie already rated by this user.");
            throw new IllegalArgumentException("Movie already rated by this user.");
        }
        Movie movie = movieRepository.findById(ratingDTO.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Movie not found"));

        Rating rating = new Rating();
        rating.setMovie(movie);
        rating.setUserId(user.getId());
        rating.setRating(ratingDTO.getRating());

        ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public void removeMovieRating(Long movieId, String email) {
        User user = userService.findByEmail(email);
        ratingRepository.deleteByMovieIdAndUserId(movieId, user.getId());
    }

    @Override
    public List<MovieRatingDTO> getMyMovieRatings(String email) {
        User user = userService.findByEmail(email);
        return ratingRepository.findAllByUserId(user.getId()).stream().map(
                rating -> modelMapper.map(rating, MovieRatingDTO.class)
        ).toList( );
    }
}
