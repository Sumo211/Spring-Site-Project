package com.leon.user;

import com.leon.infrastructure.jpa.AbstractEntityId;

public class UserId extends AbstractEntityId<Long> {

    protected UserId() {

    }

    public UserId(Long id) {
        super(id);
    }

}
