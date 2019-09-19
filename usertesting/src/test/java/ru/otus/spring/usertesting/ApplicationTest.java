package ru.otus.spring.usertesting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.usertesting.dao.QuestionDAO;
import ru.otus.spring.usertesting.dto.Question;
import ru.otus.spring.usertesting.services.PersonalTest;
import ru.otus.spring.usertesting.services.TestRunner;
import ru.otus.spring.usertesting.services.TestRunnerImpl;

import java.util.Arrays;

import static org.mockito.internal.verification.VerificationModeFactory.times;

@DisplayName("Задаем вопросы")
@ExtendWith(SpringExtension.class)
@Import(TestRunnerImpl.class)
public class ApplicationTest {

    @Autowired
    private TestRunner testRunner;

    @MockBean
    private PersonalTest personalTest;

    @MockBean
    private QuestionDAO questionDAO;

    @Test
    public void runTesting() {
        Question question1 = new Question();
        question1.setNumber(1);
        Question question2 = new Question();
        question2.setNumber(2);
        Mockito.when(questionDAO.getAllQuestions()).thenReturn(Arrays.asList(question1, question2));

        testRunner.runTesting();

        Mockito.verify(personalTest, times(1)).askFIO();
        Mockito.verify(personalTest, times(1)).askQuestion(question1);
        Mockito.verify(personalTest, times(1)).askQuestion(question2);
        Mockito.verify(personalTest, times(1)).printResult();

    }
}
