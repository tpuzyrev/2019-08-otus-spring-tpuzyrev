package ru.otus.spring.usertesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.usertesting.services.TestRunner;

@SpringBootApplication
public class UserTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserTestingApplication.class, args).getBean(TestRunner.class).runTesting();
    }

}
