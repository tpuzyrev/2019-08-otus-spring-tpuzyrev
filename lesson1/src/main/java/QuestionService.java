import java.io.IOException;

public interface QuestionService {
    Iterable<Question> getAllQuestions() throws IOException;

    void initQueryList() throws IOException;
}
