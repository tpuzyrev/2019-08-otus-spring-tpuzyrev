package ru.otus.spring.services;

import ru.otus.spring.dto.Question;

import java.util.List;

public interface IQuestionDAO {
    List<Question> getAllQuestions();
}
