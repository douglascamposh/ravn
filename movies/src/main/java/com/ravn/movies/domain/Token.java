package com.ravn.movies.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Token {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String token;

}
