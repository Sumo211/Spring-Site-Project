package com.leon.film;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;

public interface FilmService {

    Film createFilm(String code, String type, OffsetDateTime releaseDate, String description, MultipartFile cover) throws IOException;

}
