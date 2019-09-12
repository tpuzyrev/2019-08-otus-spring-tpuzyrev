package ru.otus.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer {

    private boolean isCorrect;

    private String text;

    public Answer(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}
