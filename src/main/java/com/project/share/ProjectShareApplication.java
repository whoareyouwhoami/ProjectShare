package com.project.share;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.project.share.dao")
@EnableElasticsearchRepositories(basePackages = "com.project.share.es")
public class ProjectShareApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectShareApplication.class, args);
    }
}
