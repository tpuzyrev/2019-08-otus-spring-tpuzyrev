package ru.otus.spring.jpalibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("ru.otus.spring.jpalibrary.domain")
public class JPALibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(JPALibraryApplication.class, args);
    }

}