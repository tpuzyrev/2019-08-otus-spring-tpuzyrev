package ru.otus.spring.usertesting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import ru.otus.spring.usertesting.config.AppProperties;
import ru.otus.spring.usertesting.config.MessageKey;
import ru.otus.spring.usertesting.dao.QuestionDAO;
import ru.otus.spring.usertesting.dto.Question;
import ru.otus.spring.usertesting.util.CustomMessageSource;

import java.util.Iterator;
import java.util.Locale;

@ShellComponent
public class TestRunnerImpl implements TestRunner {

    final private QuestionDAO questionDAO;
    final private PersonalTest personalTest;
    final private UserInteraction console;
    final private CustomMessageSource message;
    final private AppProperties appProperties;

    @Autowired
    public TestRunnerImpl(QuestionDAO questionDAO,
                          PersonalTest personalTest,
                          UserInteraction consoleInteraction,
                          CustomMessageSource message,
                          AppProperties appProperties) {
        this.questionDAO = questionDAO;
        this.personalTest = personalTest;
        this.console = consoleInteraction;
        this.message = message;
        this.appProperties = appProperties;
    }

    @ShellMethod(key = "run-testing", value = "Начать тестирование")
    public void runTesting(@ShellOption() String locale) {
        if (locale != null && !locale.equals(appProperties.getLocale())) {
            message.setLocale(new Locale(locale));
            appProperties.setLocale(locale);
            questionDAO.setFileName(appProperties.getFileLocaleName());
        }
        console.display(message.getMessage(MessageKey.HELLO) + "\n");
        console.display(message.getMessage(MessageKey.HELLO2) + "\n\n");
        Iterator<Question> it = questionDAO.getAllQuestions().iterator();
        personalTest.askFIO();
        while (it.hasNext()) {
            personalTest.askQuestion(it.next());
        }
        personalTest.printResult();
    }
}
