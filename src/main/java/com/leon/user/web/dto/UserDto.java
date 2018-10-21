package com.leon.user.web.dto;

import com.leon.user.User;
import com.leon.user.UserId;
import com.leon.user.UserRole;
import lombok.Value;

@Value
public class UserDto {

    private final UserId id;

    private final String email;

    private final UserRole role;

    public static UserDto fromUser(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getRole());
    }

}
