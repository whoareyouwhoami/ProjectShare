package com.project.share;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.project.share.dao")
@EnableRedisRepositories(basePackages = "com.project.share.redis")
// @EnableElasticsearchRepositories(basePackages = "com.project.share.es")
public class ProjectShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectShareApplication.class, args);
    }

}
