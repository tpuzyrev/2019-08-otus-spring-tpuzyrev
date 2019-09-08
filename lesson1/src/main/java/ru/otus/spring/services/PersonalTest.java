package ru.otus.spring.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.dto.Answer;
import ru.otus.spring.dto.Question;
import ru.otus.spring.util.CustomMessageSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class PersonalTest {

    final IConsoleInteraction console;

    final CustomMessageSource message;

    int questionCounter = 0;

    int errorCounter = 0;

    StringBuilder result = new StringBuilder();

    private String fio;


    @Autowired
    public PersonalTest(IConsoleInteraction consoleInteraction, CustomMessageSource message) {
        this.console = consoleInteraction;
        this.message = message;
        console.display(message.getMessage("message.Hello", null) + "\n");
        console.display(message.getMessage("message.Hello2", null) + "\n\n");
    }

    public void askFIO() {
        console.display(message.getMessage("message.fio", null));
        fio = console.getInputText();
        result.append(message.getMessage("message.participant", null)).append(fio).append("\n");
    }

    public void askQuestion(Question question) {
        String queryText = message.getMessage("message.question", new Object[] {question.getNumber(), question.getText()});
        questionCounter++;
        console.display(queryText);
        int i = 0;
        List<Integer> rightAnswerNumber = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        for (Answer answer : question.getAnswerList()) {
            String text = String.format("\t\t%s", message.getMessage("message.variant", new Object[] {++i, answer.getText()}));
            map.put(i, text);
            console.display(text);
            if (answer.isCorrect) {
                rightAnswerNumber.add(i);
            }
        }
        if (question.hasSeveralVariant()) {
            console.display(message.getMessage("message.severalVariant", null));
        }
        List<String> personAnswerList = getPersonAnswerList(map, question.getRightCountAnswer().intValue(), rightAnswerNumber);

        result.append(queryText).append("\n");
        result.append(message.getMessage("message.yourAnswer", null)).append("\n");
        personAnswerList.stream().forEach(s -> result.append(s).append("\n"));
    }

    private List<String> getPersonAnswerList(Map<Integer, String> map, int answerCount, List<Integer> rightAnswerNumber) {
        List<String> personAnswerList = new ArrayList<>();
        int i = 0;
        while (i < answerCount) {
            Integer variantNumber = console.getInputNumber(map.keySet());
            i++;
            personAnswerList.add(map.get(variantNumber));
            if (!rightAnswerNumber.contains(variantNumber)) {
                errorCounter++;
            }
        }

        return personAnswerList;
    }

    public void printResult() {
        console.display(result.toString());
        console.display(message.getMessage("message.result", new Object[] {questionCounter, questionCounter - errorCounter}));
    }
}

