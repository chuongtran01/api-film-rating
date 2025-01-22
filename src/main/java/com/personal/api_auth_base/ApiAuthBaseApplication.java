package com.personal.api_auth_base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableJpaRepositories
@EnableMethodSecurity
@EnableJpaAuditing
public class ApiAuthBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAuthBaseApplication.class, args);
    }

}
