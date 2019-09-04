import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionServiceImpl implements QuestionService {

    final private InputStream inputStream;

    final private List<Question> questionList = new ArrayList<Question>();

    boolean initiated = false;

    public QuestionServiceImpl(QuestionFileName fileName) {
        inputStream = getClass().getResourceAsStream(fileName.getFileName());
    }

    public Iterable<Question> getAllQuestions() throws IOException {

        if (initiated) {
            return questionList;
        } else {
            initQueryList();
        }

        return questionList;
    }

    public void initQueryList() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(";");
            int i = 0;
            Question question = new Question();
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
                        throw new RuntimeException("Error data:" + line);
                }
                i++;
            }
            questionList.add(question);

        }
    }
}
