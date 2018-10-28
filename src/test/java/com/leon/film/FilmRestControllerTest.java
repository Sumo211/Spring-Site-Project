package com.leon.film;

import com.leon.film.web.FilmRestController;
import com.leon.user.Users;
import com.leon.utils.ControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static com.leon.utils.SecurityHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ControllerTest(FilmRestController.class)
public class FilmRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FilmService filmService;

    @Test
    public void testAdminIsAbleToCreateFilm_OK() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.ADMIN_EMAIL, Users.ADMIN_PWD);

        String code = "ARM";
        String type = "XXX";
        OffsetDateTime releaseDate = OffsetDateTime.parse("2018-10-27T20:00:00.000+00:00");
        String description = "desc";
        MockMultipartFile cover = createMockImage();

        when(filmService.createFilm(eq(code), eq(type), any(OffsetDateTime.class), eq(description), any(MockMultipartFile.class))).thenReturn(new Film(new FilmId(1L), code, type, releaseDate, description));

        mvc.perform(multipart("/api/films")
                    .file(cover)
                    .header(AUTHORIZATION_HEADER, bearer(accessToken))
                    .param("code", code)
                    .param("type", type)
                    .param("description", description))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("code").value(code))
                .andExpect(jsonPath("type").value(type))
                .andExpect(jsonPath("releaseDate").value(releaseDate))
                .andExpect(jsonPath("description").value(description));
    }

    private MockMultipartFile createMockImage() {
        return new MockMultipartFile("cover", "cover.png", "image/png", new byte[]{1, 2, 3});
    }

}
