package com.leon.utils;

import com.leon.infrastructure.security.*;
import com.leon.user.Users;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@TestConfiguration
@Import({SecurityConfiguration.class, AuthorizationServerConfiguration.class, ResourceServerConfiguration.class})
public class ControllerTestConfiguration {

    @Bean
    public UserDetailsService customUserDetailsService() {
        return username -> {
            switch (username) {
                case Users.USER_EMAIL:
                    return new AppUserDetails(Users.user());
                case Users.ADMIN_EMAIL:
                    return new AppUserDetails(Users.admin());
                default:
                    throw new UsernameNotFoundException(username);
            }
        };
    }

    @Bean
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }

}
