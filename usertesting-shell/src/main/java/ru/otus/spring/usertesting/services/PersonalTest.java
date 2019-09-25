package ru.otus.spring.usertesting.services;

import ru.otus.spring.usertesting.dto.Question;

public interface PersonalTest {

    void askFIO();

    void askQuestion(Question question);

    void printResult();
}
