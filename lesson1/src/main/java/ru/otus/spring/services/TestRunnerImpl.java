package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.Question;

import java.util.Iterator;

@Service
public class TestRunnerImpl implements TestRunner {

    private QuestionDAO questionDAO;

    @Autowired
    public TestRunnerImpl(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public void runTesting(PersonalTest personalTest) {

        Iterator<Question> it = questionDAO.getAllQuestions().iterator();
        personalTest.askFIO();
        while (it.hasNext()) {
            personalTest.askQuestion(it.next());
        }
        personalTest.printResult();
    }
}
