package ru.otus.spring.usertesting.dao;

import ru.otus.spring.usertesting.dto.Question;

import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestions();
}
