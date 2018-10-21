package com.leon.film.web.dto;

import com.leon.film.web.ValidFilmCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmCreatedDto {

    @ValidFilmCode
    private String code;

    private String type;

    private String description;

}
