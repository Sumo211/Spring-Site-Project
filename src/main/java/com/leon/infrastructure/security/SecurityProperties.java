package com.leon.infrastructure.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "xxx-secure")
@Data
public class SecurityProperties {

    private String clientId;

    private String secret;

}
