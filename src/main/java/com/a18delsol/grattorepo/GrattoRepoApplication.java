package com.a18delsol.grattorepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class GrattoRepoApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrattoRepoApplication.class, args);
    }
}
