package com.ravn.movies.repository;

import com.ravn.movies.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITokenRepository extends JpaRepository<Token, Long> {
    Token findByUserId(Long userId);
    Token findByToken(String token);
}
