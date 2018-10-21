package com.leon.infrastructure.security;

import com.leon.user.User;
import com.leon.user.UserId;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Getter
public class AppUserDetails extends org.springframework.security.core.userdetails.User {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserId userId;

    public AppUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name())));
        this.userId = user.getId();
    }

}
