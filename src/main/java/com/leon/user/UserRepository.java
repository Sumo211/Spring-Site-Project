package com.leon.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, UserId>, CustomUserRepository {

    Optional<User> findByEmailIgnoreCase(String email);

}
