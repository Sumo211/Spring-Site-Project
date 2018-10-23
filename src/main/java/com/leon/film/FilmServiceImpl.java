package com.leon.film;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film createFilm(String code, String type, String description, MultipartFile cover) throws IOException {
        Film film = new Film(filmRepository.getNextId(), code, type, description);
        film.setCover(cover.getBytes());
        return filmRepository.save(film);
    }

}
