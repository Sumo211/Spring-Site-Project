package com.leon.user;

import com.leon.infrastructure.jpa.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, UserId> {

    Optional<User> findByEmailIgnoreCase(String email);

}
