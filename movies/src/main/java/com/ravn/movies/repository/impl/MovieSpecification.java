package com.ravn.movies.repository.impl;

import com.ravn.movies.domain.Movie;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MovieSpecification implements Specification<Movie> {

    private final String query;
    private final Long category;
    private final Long year;

    public MovieSpecification(String query, Long category, Long year) {
        this.query = query;
        this.category = category;
        this.year = year;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (query != null && !query.isEmpty()) {
            String lowercaseQuery = "%" + query.toLowerCase() + "%";
            Predicate namePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    lowercaseQuery
            );
            Predicate synopsisPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("synopsis")),
                    lowercaseQuery
            );
            predicates.add(criteriaBuilder.or(namePredicate, synopsisPredicate));
        }

        if (category != null) {
            predicates.add(criteriaBuilder.equal(root.get("category").get("id"), category));
        }

        if (year != null) {
            predicates.add(criteriaBuilder.equal(root.get("year"), year));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}