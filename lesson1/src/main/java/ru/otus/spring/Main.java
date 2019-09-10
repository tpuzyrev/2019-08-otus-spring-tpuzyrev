package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.services.PersonalTest;
import ru.otus.spring.services.TestRunner;

@ComponentScan
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestRunner testRunner = context.getBean(TestRunner.class);
        testRunner.runTesting(context.getBean(PersonalTest.class));
    }
}
