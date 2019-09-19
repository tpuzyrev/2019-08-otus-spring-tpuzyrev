package ru.otus.spring.usertesting.dao;


import ru.otus.spring.usertesting.dto.Answer;
import ru.otus.spring.usertesting.dto.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionDAOImpl implements QuestionDAO {

    final private String fileName;

    final private List<Question> questionList = new ArrayList<Question>();

    private boolean initiated = false;

    public QuestionDAOImpl(String fileName) {
        this.fileName = fileName;
    }

    public List<Question> getAllQuestions() {

        if (initiated) {
            return questionList;
        } else {
            initQueryList();
        }

        return questionList;
    }

    public void initQueryList() {
        InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) {
                    break;
                }
            } catch (IOException e) {
                System.out.println(String.format("Error reading file %s!", fileName));
                break;
            }
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(";");
            int i = 0;
            Question question = new Question();
            Answer answer = null;
            while (scanner.hasNext()) {
                String data = scanner.next();
                switch (i) {
                    case 0:
                        question.setNumber(Integer.parseInt(data));
                        break;
                    case 1:
                        question.setText(data);
                        break;
                    default:
                        if (answer == null) {
                            answer = new Answer(data);
                        } else {
                            answer.setCorrect(Integer.parseInt(data) == 1);
                            question.getAnswerList().add(answer);
                            answer = null;
                        }

                }
                i++;
            }
            scanner.close();
            questionList.add(question);
        }
        try {
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
