package com.leon.infrastructure.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.leon"}, repositoryBaseClass = BaseRepositoryImpl.class)
@Configuration
public class JpaConfiguration {

}
