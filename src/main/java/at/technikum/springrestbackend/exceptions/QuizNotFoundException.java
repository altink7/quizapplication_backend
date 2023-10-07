package at.technikum.springrestbackend.exceptions;

public class QuizNotFoundException extends QuizException {
    public QuizNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public QuizNotFoundException() {
        super("Quiz not found!");
    }
}
