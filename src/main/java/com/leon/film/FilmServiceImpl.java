package com.leon.film;

import org.springframework.stereotype.Service;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film createFilm(String code, String type, String description) {
        Film film = new Film(filmRepository.getNextId(), code, type, description);
        return filmRepository.save(film);
    }

}
