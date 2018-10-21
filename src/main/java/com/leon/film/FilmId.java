package com.leon.film;

import com.leon.infrastructure.jpa.AbstractEntityId;

public class FilmId extends AbstractEntityId<Long> {

    protected FilmId() {

    }

    public FilmId(Long id) {
        super(id);
    }

}
