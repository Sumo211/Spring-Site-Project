package com.leon.film;

public interface CustomFilmRepository {

    FilmId getNextId();

    Film getFilmAndItsRatings(FilmId id);

}
