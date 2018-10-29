package com.leon.film;

import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;

public class FilmSpecifications {

    public static Specification<Film> isNewFilm() {
        OffsetDateTime now = OffsetDateTime.now();
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("releaseDate"), now.minusDays(7), now);
    }

    public static Specification<Film> hasType(String type) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }

}
