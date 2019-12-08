package ru.otus.spring.mongolibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MongoDBLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDBLibraryApplication.class, args);
    }
}