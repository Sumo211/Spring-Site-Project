package com.leon.film;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilmService {

    Film createFilm(String code, String type, String description, MultipartFile cover) throws IOException;

}
