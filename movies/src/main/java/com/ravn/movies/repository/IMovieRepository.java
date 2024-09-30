package com.ravn.movies.repository;

import com.ravn.movies.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IMovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
}
