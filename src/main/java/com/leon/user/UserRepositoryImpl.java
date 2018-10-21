package com.leon.user;

import com.leon.infrastructure.jpa.UniqueIdGenerator;

public class UserRepositoryImpl implements CustomUserRepository {

    private final UniqueIdGenerator<Long> generator;

    public UserRepositoryImpl(UniqueIdGenerator<Long> generator) {
        this.generator = generator;
    }

    @Override
    public UserId getNextId() {
        return new UserId(generator.getNextUniqueId());
    }

}
