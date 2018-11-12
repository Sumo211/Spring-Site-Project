package com.leon.film;

public interface FilmRepositoryCustom {

    FilmId getNextId();

    Film getFilmAndItsRatings(FilmId id);

}
