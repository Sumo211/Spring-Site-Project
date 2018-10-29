package com.leon.film;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FilmRepository extends JpaRepository<Film, FilmId>, JpaSpecificationExecutor<Film>, CustomFilmRepository {

}
