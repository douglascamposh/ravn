package com.ravn.movies.repository;

import com.ravn.movies.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
