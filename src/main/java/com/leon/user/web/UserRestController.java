package com.leon.user.web;

import com.leon.infrastructure.security.AppUserDetails;
import com.leon.user.User;
import com.leon.user.UserService;
import com.leon.user.web.dto.UserCreatedDto;
import com.leon.user.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal(errorOnInvalidType = true) AppUserDetails userDetails) {
        User currentUser = userService.getUser(userDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(userDetails.getUserId()));
        return UserDto.fromUser(currentUser);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserCreatedDto userCreatedDto) {
        User renter = userService.createUser(userCreatedDto.getEmail(), userCreatedDto.getPassword());
        return UserDto.fromUser(renter);
    }

}
