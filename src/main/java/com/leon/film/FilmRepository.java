package com.leon.film;

import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, FilmId>, CustomFilmRepository {

}
