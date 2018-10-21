package com.leon.film;

import com.leon.infrastructure.jpa.UniqueIdGenerator;

public class FilmRepositoryImpl implements CustomFilmRepository {

    private final UniqueIdGenerator<Long> generator;

    public FilmRepositoryImpl(UniqueIdGenerator<Long> generator) {
        this.generator = generator;
    }

    @Override
    public FilmId getNextId() {
        return new FilmId(generator.getNextUniqueId());
    }

}
