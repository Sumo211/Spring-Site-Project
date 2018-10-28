package com.leon.film.web;

import com.leon.film.Film;
import com.leon.film.FilmService;
import com.leon.film.web.dto.FilmCreatedDto;
import com.leon.film.web.dto.FilmDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/films")
public class FilmRestController {

    private final FilmService filmService;

    public FilmRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PreAuthorize("#oauth2.hasScope('write') && hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto createFilm(@Valid FilmCreatedDto filmCreatedDto) throws IOException {
        Film film = filmService.createFilm(filmCreatedDto.getCode(), filmCreatedDto.getType(), filmCreatedDto.getReleaseDate(),
                filmCreatedDto.getDescription(), filmCreatedDto.getCover());
        return FilmDto.fromFilm(film);
    }

}
