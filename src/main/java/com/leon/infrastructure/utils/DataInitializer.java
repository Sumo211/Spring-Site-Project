package com.leon.infrastructure.utils;

import com.leon.user.User;
import com.leon.user.UserRepository;
import com.leon.user.UserRole;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "xxx-db", value = "init-data", havingValue = "true")
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        User admin = new User(userRepository.getNextId(), "admin@mail.com", passwordEncoder.encode("pwd@123"), UserRole.ADMIN);
        userRepository.save(admin);
    }

}
