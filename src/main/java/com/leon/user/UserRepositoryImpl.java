package com.leon.user;

import com.leon.infrastructure.jpa.SnowFlakeGenerator;
import com.leon.infrastructure.jpa.UniqueIdGenerator;

public class UserRepositoryImpl implements CustomUserRepository {

    private final UniqueIdGenerator<Long> generator = new SnowFlakeGenerator();

    @Override
    public UserId getNextId() {
        return new UserId(generator.getNextUniqueId());
    }

}
