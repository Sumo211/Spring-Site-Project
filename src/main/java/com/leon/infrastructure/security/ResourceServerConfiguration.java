package com.leon.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@EnableResourceServer
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${xxx-resource.id:xxx}")
    private String RESOURCE_ID;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
            .and()
            .antMatcher("/api/**")
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .anyRequest().authenticated();
        // @formatter:on
    }

    // Custom oauth2 request matcher
    /*public static class OAuthRequestedMatcher implements RequestMatcher {

        @Override
        public boolean matches(HttpServletRequest request) {
            String oauthHeader = request.getHeader("Authorization");
            boolean haveOAuth2Token = (oauthHeader != null) && oauthHeader.startsWith("Bearer");
            boolean haveAccessToken = request.getParameter("access_token") != null;
            return haveAccessToken || haveOAuth2Token;
        }

    }*/

}
