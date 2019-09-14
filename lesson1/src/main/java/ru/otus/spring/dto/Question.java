package ru.otus.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
public class Question {

    private int number;

    private String text;

    private List<Answer> answerList = new ArrayList<>();

    public boolean hasSeveralVariant() {
        return answerList.stream().filter(answer -> answer.isCorrect()).count() > 1;
    }
}
