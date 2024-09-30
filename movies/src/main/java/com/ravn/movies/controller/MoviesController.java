package com.ravn.movies.controller;

import com.ravn.movies.domain.Movie;
import com.ravn.movies.domain.Rating;
import com.ravn.movies.dto.MovieDTO;
import com.ravn.movies.dto.MovieFormDTO;
import com.ravn.movies.dto.MovieRatingDTO;
import com.ravn.movies.dto.PaginationResponse;
import com.ravn.movies.dto.RatingDTO;
import com.ravn.movies.service.IMovieService;
import com.ravn.movies.service.IRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(MoviesController.BASE_CTRL_URL)
public class MoviesController {
    public static final String BASE_CTRL_URL = "api/v1/movies";

    private final IMovieService movieService;
    private final IRatingService ratingService;
    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);

    public MoviesController(IMovieService movieService, IRatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping()
    public PaginationResponse<MovieDTO> searchMovies(
            @RequestParam(value = "query", defaultValue = "") String query,
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(value = "year", required = false) Long year,
            @PageableDefault(size = 10, sort = "year", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "sortBy", required = false) String sortBy, // Add sortBy parameter
            @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection
    ) {
        return movieService.findAll(query, category, year, pageable, sortBy, sortDirection);
    }

    @PostMapping
    public MovieDTO saveMovie(@Valid @RequestBody MovieFormDTO movie, Principal principal) {
        String email = principal.getName();
        return movieService.save(movie, email);
    }

    @GetMapping("/{movieId}")
    public MovieDTO getMovie(@PathVariable Long movieId) {
        return movieService.findById(movieId);
    }

    @PutMapping("/{id}")
    public MovieDTO updateMovie(@PathVariable Long id, @Valid @RequestBody MovieFormDTO movie, Principal principal) {
        String email = principal.getName();
        return movieService.update(id, movie, email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{movieId}/rate")
    public void rateMovie(@PathVariable Long movieId, @Valid @RequestBody RatingDTO ratingDTO, Principal principal) {
        String email = principal.getName();
        ratingService.rateMovie(ratingDTO, email);
    }

    @DeleteMapping("/{movieId}/rate")
    public void removeMovieRating(@PathVariable Long movieId, Principal principal) {
        String email = principal.getName();
        ratingService.removeMovieRating(movieId, email);
    }

    @GetMapping("/ratings")
    public List<MovieRatingDTO> getMyMovieRatings(Principal principal) {
        String email = principal.getName();
        return ratingService.getMyMovieRatings(email);
    }
}
