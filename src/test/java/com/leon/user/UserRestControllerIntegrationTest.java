package com.leon.user;

import com.leon.utils.SecurityHelperForRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "INTEGRATION-TEST")
public class UserRestControllerIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testAdminIsUnableToCreateAFilmIfCoverImageIsTooBig() {
        String code = "ARM";
        String type = "XXX";
        String releaseDate = "2018-10-27T20:00:00.000+00:00";
        String description = "desc";

        SecurityHelperForRestAssured.givenAuthenticatedUser(serverPort, "admin@mail.com", "pwd@123")
                .when()
                .multiPart("cover", new MultiPartSpecBuilder(new byte[2000000])
                        .fileName("cover.png")
                        .mimeType("image/png")
                        .build())
                .formParam("code", code)
                .formParam("type", type)
                .formParam("releaseDate", releaseDate)
                .formParam("description", description)
                .post("/api/films")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
