package com.leon.film.web.dto;

import com.leon.film.web.ValidFilmCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmCreatedDto {

    @ValidFilmCode
    private String code;

    private String type;

    private OffsetDateTime releaseDate;

    private String description;

    private MultipartFile cover;

}
