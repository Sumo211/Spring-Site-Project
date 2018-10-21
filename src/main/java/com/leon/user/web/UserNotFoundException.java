package com.leon.user.web;

import com.leon.user.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {

    UserNotFoundException(UserId userId) {
        super(String.format("Could not find user with id %s", userId.asString()));
    }

}
