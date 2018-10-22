package com.leon.user.web.dto;

import com.leon.user.web.ValidUser;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ValidUser
public class UserCreatedDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 100)
    private String password;

}
