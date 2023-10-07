package at.technikum.springrestbackend.exceptions;

public class QuestionNotFoundException extends QuizException {
    public QuestionNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public QuestionNotFoundException() {
        super("Question not found!");
    }
}
