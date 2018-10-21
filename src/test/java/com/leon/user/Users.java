package com.leon.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.ThreadLocalRandom;

public final class Users {

    public static final String USER_EMAIL = "user@mail.com";

    public static final String USER_PWD = "user";

    public static final String ADMIN_EMAIL = "admin@mail.com";

    public static final String ADMIN_PWD = "admin";

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private static final User USER = User.createUser(newRandomUserId(), USER_EMAIL, PASSWORD_ENCODER.encode(USER_PWD));

    private static final User ADMIN = User.createAdmin(newRandomUserId(), ADMIN_EMAIL, PASSWORD_ENCODER.encode(ADMIN_PWD));

    private Users() {

    }

    static User newRandomUser() {
        return newRandomUser(newRandomUserId());
    }

    public static User user() {
        return USER;
    }

    public static User admin() {
        return ADMIN;
    }

    static User newUser(String username, String password) {
        return User.createUser(newRandomUserId(), username, password);
    }

    private static User newRandomUser(UserId userId) {
        String uniqueId = userId.asString().substring(5);
        return User.createUser(userId, "user-" + uniqueId + "@mail.com", PASSWORD_ENCODER.encode("random-user"));
    }

    private static UserId newRandomUserId() {
        return new UserId(ThreadLocalRandom.current().nextLong());
    }

}
