package com.leon.film.web.dto;

import com.leon.film.Film;
import com.leon.film.FilmId;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class FilmDto {

    private FilmId id;

    private String code;

    private String type;

    private OffsetDateTime releaseDate;

    private String description;

    public static FilmDto fromFilm(Film film) {
        return new FilmDto(film.getId(), film.getCode(), film.getType(), film.getReleaseDate(), film.getDescription());
    }

}
