package com.leon.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String username, String password) {
        User user = User.createUser(userRepository.getNextId(), username, passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(UserId userId) {
        return userRepository.findById(userId);
    }

}
