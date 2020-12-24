package com.laurentiuspilca.ssia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableEnvironment environment = new StandardEnvironment();
//        environment.setActiveProfiles("jpa");
        environment.setActiveProfiles("inMemory");

        SpringApplication sa = new SpringApplication(Main.class);
        sa.setEnvironment(environment);
        sa.run(args);
    }
}
