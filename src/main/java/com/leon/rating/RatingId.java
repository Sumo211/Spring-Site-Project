package com.leon.rating;

import com.leon.infrastructure.jpa.AbstractEntityId;

public class RatingId extends AbstractEntityId<Long> {

    protected RatingId() {

    }

    public RatingId(Long id) {
        super(id);
    }

}
