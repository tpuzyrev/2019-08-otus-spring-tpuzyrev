package ru.otus.spring.jdbclibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class JdbcLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcLibraryApplication.class, args);
    }

}