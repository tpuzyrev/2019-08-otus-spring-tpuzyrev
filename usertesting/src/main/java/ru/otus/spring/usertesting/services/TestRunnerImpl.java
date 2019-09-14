package ru.otus.spring.usertesting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.usertesting.dao.QuestionDAO;
import ru.otus.spring.usertesting.dto.Question;

import java.util.Iterator;

@Service
public class TestRunnerImpl implements TestRunner {

    final private QuestionDAO questionDAO;
    final private PersonalTest personalTest;

    @Autowired
    public TestRunnerImpl(QuestionDAO questionDAO, PersonalTest personalTest) {
        this.questionDAO = questionDAO;
        this.personalTest = personalTest;
    }

    public void runTesting() {

        Iterator<Question> it = questionDAO.getAllQuestions().iterator();
        personalTest.askFIO();
        while (it.hasNext()) {
            personalTest.askQuestion(it.next());
        }
        personalTest.printResult();
    }
}
