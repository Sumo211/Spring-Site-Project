package com.leon.user;

import com.leon.infrastructure.jpa.UniqueIdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final UniqueIdGenerator<Long> generator;

    public UserRepositoryImpl(@Qualifier("snowFlakeGenerator") UniqueIdGenerator<Long> generator) {
        this.generator = generator;
    }

    @Override
    public UserId getNextId() {
        return new UserId(generator.getNextUniqueId());
    }

}
