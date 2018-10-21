package com.leon.user;

import java.util.Optional;

public interface UserService {

    Optional<User> getUser(UserId userId);

    User createUser(String username, String password);

}
