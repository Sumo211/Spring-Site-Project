package com.leon.film;

import com.leon.infrastructure.jpa.BaseRepository;

public interface FilmRepository extends BaseRepository<Film, FilmId>, CustomFilmRepository {

}
