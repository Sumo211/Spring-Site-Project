package com.leon.utils;

import io.restassured.specification.RequestSpecification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public final class SecurityHelperForRestAssured {

    private static final String IT_TEST_CLIENT_ID = "it-test-client-id";

    private static final String IT_TEST_CLIENT_SECRET = "it-test-secret";

    private SecurityHelperForRestAssured() {

    }

    public static RequestSpecification givenAuthenticatedUser(int serverPort, String username, String password) {
        OAuth2RestTemplate template = new OAuth2RestTemplate(createResourceDetails(serverPort, username, password));
        OAuth2AccessToken accessToken = template.getAccessToken();
        return given().auth().preemptive().oauth2(accessToken.getValue());
    }

    private static ResourceOwnerPasswordResourceDetails createResourceDetails(int serverPort, String username, String password) {
        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        details.setUsername(username);
        details.setPassword(password);
        details.setClientId(IT_TEST_CLIENT_ID);
        details.setClientSecret(IT_TEST_CLIENT_SECRET);
        details.setScope(Arrays.asList("read", "write"));
        details.setAccessTokenUri(String.format("http://localhost:%s/oauth/token", serverPort));
        return details;
    }

}
