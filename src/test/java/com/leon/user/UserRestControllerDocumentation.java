package com.leon.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leon.user.web.UserRestController;
import com.leon.user.web.dto.UserCreatedDto;
import com.leon.utils.ControllerTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static com.leon.utils.SecurityHelper.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ControllerTest(controllers = UserRestController.class)
public class UserRestControllerDocumentation {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private RestDocumentationResultHandler resultHandler;

    @Before
    public void setUp() {
        resultHandler = document("{method-name}", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint(), removeMatchingHeaders("X.*", "Pragma", "Expires")));

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(resultHandler)
                .build();
    }

    @Test
    public void getTheDetailsWhenNotLoggedIn() throws Exception {
        mvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getTheDetailsAsUser() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.USER_EMAIL, Users.USER_PWD);

        when(userService.getUser(Users.user().getId())).thenReturn(Optional.of(Users.user()));

        mvc.perform(get("/api/users/me")
                .header(AUTHORIZATION_HEADER, bearer(accessToken)))
                .andExpect(status().isOk())
                .andDo(resultHandler.document(
                        responseFields(
                                fieldWithPath("id").description("The unique id of the user."),
                                fieldWithPath("email").description("The email address of the user."),
                                fieldWithPath("role").description("The security role of the user.")
                        )
                ));
    }

    @Test
    public void createUser() throws Exception {
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
                .andDo(resultHandler.document(
                        requestFields(
                                fieldWithPath("email").description("The email address of the user to be created."),
                                fieldWithPath("password").description("The password for the new user.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("The unique id of the user."),
                                fieldWithPath("email").description("The email address of the user."),
                                fieldWithPath("role").description("The security role of the user.")
                        )
                ));
    }

}
