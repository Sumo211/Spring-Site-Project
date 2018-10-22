package com.leon.film.web;

import com.leon.film.Film;
import com.leon.film.FilmService;
import com.leon.film.web.dto.FilmCreatedDto;
import com.leon.film.web.dto.FilmDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public FilmDto createFilm(@Valid @RequestBody FilmCreatedDto filmCreatedDto) {
        Film film = filmService.createFilm(filmCreatedDto.getCode(), filmCreatedDto.getType(), filmCreatedDto.getDescription());
        return FilmDto.fromFilm(film);
    }

}
