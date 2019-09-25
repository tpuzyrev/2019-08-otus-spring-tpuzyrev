package ru.otus.spring.usertesting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
