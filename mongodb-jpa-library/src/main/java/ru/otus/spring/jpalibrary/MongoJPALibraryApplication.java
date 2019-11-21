package ru.otus.spring.jpalibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import ru.otus.spring.jpalibrary.config.BookGenerator;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MongoJPALibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoJPALibraryApplication.class, args);
    }
}