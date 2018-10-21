package com.leon.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leon.user.web.UserRestController;
import com.leon.user.web.dto.UserCreatedDto;
import com.leon.utils.ControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.leon.utils.SecurityHelper.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ControllerTest(value = UserRestController.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testGetTheDetails_NotAuthenticated() throws Exception {
        mvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetTheDetailsAsUser_OK() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.USER_EMAIL, Users.USER_PWD);

        when(userService.getUser(Users.user().getId())).thenReturn(Optional.of(Users.user()));

        mvc.perform(get("/api/users/me")
                .header(AUTHORIZATION_HEADER, bearer(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").value(Users.USER_EMAIL))
                .andExpect(jsonPath("role").value("USER"));
    }

    @Test
    public void testCreateUser_OK() throws Exception {
        String email = "test@mail.com";
        String password = "test-pwd";

        UserCreatedDto dto = new UserCreatedDto();
        dto.setEmail(email);
        dto.setPassword(password);

        when(userService.createUser(email, password)).thenReturn(Users.newUser(email, password));

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("role").value("USER"));

        verify(userService).createUser(email, password);
    }

    @Test
    public void testCreateUser_IfPasswordTooShort() throws Exception {
        String email = "test@mail.com";
        String shortPwd = "pwd";

        UserCreatedDto dto = new UserCreatedDto();
        dto.setEmail(email);
        dto.setPassword(shortPwd);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].fieldName").value("password"));

        verify(userService, never()).createUser(email, shortPwd);
    }

}
