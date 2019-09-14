package ru.otus.spring.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.MessageKey;
import ru.otus.spring.dto.Answer;
import ru.otus.spring.dto.Question;
import ru.otus.spring.util.CustomMessageSource;

import java.util.*;
import java.util.function.Consumer;

@Component
@Getter
public class PersonalTestImpl implements PersonalTest{

    final private UserInteraction console;

    final private CustomMessageSource message;

    private int questionCounter = 0;

    private int errorCounter = 0;

    private StringBuilder result = new StringBuilder();

    private String fio;


    @Autowired
    public PersonalTestImpl(UserInteraction consoleInteraction, CustomMessageSource message) {
        this.console = consoleInteraction;
        this.message = message;
        console.display(message.getMessage(MessageKey.HELLO) + "\n");
        console.display(message.getMessage(MessageKey.HELLO2) + "\n\n");
    }

    @Override
    public void askFIO() {
        console.display(message.getMessage(MessageKey.FIO));
        fio = console.getInputText();
        result.append(message.getMessage(MessageKey.PARTICIPANT)).append(fio).append("\n");
    }

    @Override
    public void askQuestion(Question question) {
        String queryText = displayQuestion(question);
        int i = 0;
        List<Integer> rightAnswerNumber = new ArrayList<>();
        Map<Integer, String> ordinalToAnswerTextMap = new HashMap<>();
        for (Answer answer : question.getAnswerList()) {
            String text = String.format("\t\t%s", message.getMessage(MessageKey.VARIANT, new Object[] {++i, answer.getText()}));
            ordinalToAnswerTextMap.put(i, text);
            console.display(text);
            if (answer.isCorrect()) {
                rightAnswerNumber.add(i);
            }
        }
        boolean hasSeveralVariant = question.hasSeveralVariant();
        if (hasSeveralVariant) {
            console.display(message.getMessage(MessageKey.SEVERAL_VARIANT));
        }
        List<String> personAnswerList = getPersonAnswerList(ordinalToAnswerTextMap, hasSeveralVariant, rightAnswerNumber);

        result.append(queryText).append("\n");
        result.append(message.getMessage(MessageKey.YOUR_ANSWER)).append("\n");
        personAnswerList.stream().forEach(s -> result.append(s).append("\n"));
    }

    private String displayQuestion(Question question) {
        String queryText = message.getMessage(MessageKey.QUESTION, new Object[] {question.getNumber(), question.getText()});
        questionCounter++;
        console.display(queryText);
        return queryText;
    }

    private List<String> getPersonAnswerList(Map<Integer, String> map, boolean hasSeveralVariant, List<Integer> rightAnswerNumber) {
        List<String> personAnswerList = new ArrayList<>();
        List<Integer> variantNumberList = console.getInputNumber(map.keySet(), hasSeveralVariant);
        variantNumberList.forEach(variantNumber -> {
            personAnswerList.add(map.get(variantNumber));
            if (!rightAnswerNumber.contains(variantNumber)) {
                errorCounter++;
            }
        });
        return personAnswerList;
    }



    @Override
    public void printResult() {
        console.display(result.toString());
        console.display(message.getMessage(MessageKey.RESULT, new Object[] {questionCounter, questionCounter - errorCounter}));
    }
}

