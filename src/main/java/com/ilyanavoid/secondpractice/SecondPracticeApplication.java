package com.ilyanavoid.secondpractice;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecondPracticeApplication {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/employees", "postgres", "postgres").load();
        flyway.migrate();
        SpringApplication.run(SecondPracticeApplication.class, args);
    }
}
