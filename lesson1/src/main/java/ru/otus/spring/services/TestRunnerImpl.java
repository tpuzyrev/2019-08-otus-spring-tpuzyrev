package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.Question;

import java.util.Iterator;

@Service
public class TestRunnerImpl implements ITestRunner {

    private IQuestionDAO questionDAO;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public TestRunnerImpl(IQuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public void run() {

        Iterator<Question> it = questionDAO.getAllQuestions().iterator();
        PersonalTest personalTest = context.getBean(PersonalTest.class);
        personalTest.askFIO();
        while (it.hasNext()) {
            personalTest.askQuestion(it.next());
        }
        personalTest.printResult();
    }
}
