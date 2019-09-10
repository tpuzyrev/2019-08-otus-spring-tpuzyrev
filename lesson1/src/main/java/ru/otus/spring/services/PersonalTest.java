package ru.otus.spring.services;

import ru.otus.spring.dto.Question;

public interface PersonalTest {

    void askFIO();

    void askQuestion(Question question);

    void printResult();
}
