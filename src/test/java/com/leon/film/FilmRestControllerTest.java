package com.leon.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leon.film.web.FilmRestController;
import com.leon.film.web.dto.FilmCreatedDto;
import com.leon.user.Users;
import com.leon.utils.ControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.leon.utils.SecurityHelper.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ControllerTest(FilmRestController.class)
public class FilmRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;

    @Test
    public void testAdminIsAbleToCreateFilm_OK() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.ADMIN_EMAIL, Users.ADMIN_PWD);

        String code = "xxx";
        String type = "XXX";
        String description = "des";
        FilmCreatedDto dto = new FilmCreatedDto(code, type, description);

        when(filmService.createFilm(eq(code), eq(type), eq(description))).thenReturn(new Film(new FilmId(1L), code, type, description));

        mvc.perform(post("/api/films")
                .header(AUTHORIZATION_HEADER, bearer(accessToken))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("code").value(code))
                .andExpect(jsonPath("type").value(type))
                .andExpect(jsonPath("description").value(description));
    }

}
