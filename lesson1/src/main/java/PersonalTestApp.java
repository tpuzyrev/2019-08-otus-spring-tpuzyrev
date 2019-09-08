import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class PersonalTestApp {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        Iterator<Question> it = questionService.getAllQuestions().iterator();
        System.out.println("Добрый день!\nМы рады приветсвовать Вас на нашем опроснике.\n\n");
        System.out.println("Введите пожалуйста ваше ФИО.");
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        String fio = scanner.next();
        result.append("ФИО участника:").append(fio).append("\n");
        while (it.hasNext()) {
            Question question = it.next();
            String queryText = String.format("Вопрос №%s : %s", question.getNumber(), question.getText());
            System.out.println(queryText);
            result.append(queryText).append("\n");
            result.append(String.format("Ваш ответ: %s", scanner.next())).append("\n");
        }
        System.out.println("***********СПАСИБО*****************\n");
        System.out.println(result.toString());
    }
}
