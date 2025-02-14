package com.ravn.movies.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;
}
