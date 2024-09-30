package com.ravn.movies.service.impl;

import com.ravn.movies.domain.Category;
import com.ravn.movies.domain.Movie;
import com.ravn.movies.domain.User;
import com.ravn.movies.dto.MovieDTO;
import com.ravn.movies.dto.MovieFormDTO;
import com.ravn.movies.dto.PaginationResponse;
import com.ravn.movies.error.DataNotFoundException;
import com.ravn.movies.repository.ICategoryRepository;
import com.ravn.movies.repository.IMovieRepository;
import com.ravn.movies.repository.IUserRepository;
import com.ravn.movies.repository.impl.MovieSpecification;
import com.ravn.movies.service.IMovieService;
import com.ravn.movies.service.IUserService;
import com.ravn.movies.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements IMovieService {

    private final IMovieRepository movieRepository;
    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final IUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    @Cacheable(value = "movies", key = "{#query, #category, #year, #pageable.pageNumber, #pageable.pageSize, #sortBy, #sortDirection}")
    public PaginationResponse<MovieDTO> findAll(String query, Long category, Long year, Pageable pageable, String sortBy, String sortDirection) {
        MovieSpecification specification = new MovieSpecification(query, category, year);
        if (sortBy != null && !sortBy.isEmpty()) {
            Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        Page<Movie> movies = movieRepository.findAll(specification, pageable);
        List<MovieDTO> movieDTOS = movies.getContent().stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).toList();
        return new PaginationResponse<>(
            movieDTOS,
            pageable.getPageNumber(),
            pageable.getPageSize(),
            movies.getTotalElements(),
            movies.getTotalPages()
        );

    }

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public MovieDTO save(MovieFormDTO movieDTO, String email) {
        Category category = categoryRepository.findById(movieDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        User user = userService.findByEmail(email);
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        movie.setId(null);
        movie.setCategory(category);
        movie.setCreatedDate(Utils.getCurrentTimeSeconds());
        movie.setUserId(user.getId());
        return modelMapper.map(movieRepository.save(movie), MovieDTO.class);
    }

    @Override
    public MovieDTO findById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Movie movieId: " + movieId + " was not found"));
        return modelMapper.map(movie, MovieDTO.class);
    }

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public MovieDTO update(Long id, MovieFormDTO movieDto, String email) {
        User user = userService.findByEmail(email);
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Movie movieId: " + id + " was not found"));
        modelMapper.map(movieDto, movie);
        movie.setId(id);
        movie.setUserId(user.getId());
        movie = movieRepository.save(movie);
        logger.info("Movie updated successfully");
        return modelMapper.map(movie, MovieDTO.class);
    }

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public void delete(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Movie movieId: " + id + " was not found"));
        movieRepository.delete(movie);
        logger.info("Movie deleted successfully");
    }
}
