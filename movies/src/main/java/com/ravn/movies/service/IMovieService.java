package com.ravn.movies.service;
import com.ravn.movies.domain.Movie;
import com.ravn.movies.dto.MovieDTO;
import com.ravn.movies.dto.MovieFormDTO;

import com.ravn.movies.dto.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;

public interface IMovieService {
    List<Movie> findAll();
    PaginationResponse<MovieDTO> findAll(String query, Long category, Long year, Pageable pageable, String sortBy, String sortDirection);

    MovieDTO save(MovieFormDTO movieDTO, String email);

    MovieDTO findById(Long movieId);

    MovieDTO update(Long id, @Valid MovieFormDTO movie, String email);

    void delete(Long id);
}
