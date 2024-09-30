package com.ravn.movies.repository;

import com.ravn.movies.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByMovieIdAndUserId(Long movieId, Long userId);
    void deleteByMovieIdAndUserId(Long movieId, Long userId);
    List<Rating> findAllByUserId(Long userId);
}
