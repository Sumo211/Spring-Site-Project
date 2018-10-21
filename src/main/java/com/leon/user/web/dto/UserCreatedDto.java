package com.leon.user.web.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserCreatedDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 100)
    private String password;

}
