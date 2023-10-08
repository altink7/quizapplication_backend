package at.technikum.springrestbackend.exceptions;

public class AnswerNotFoundException extends QuizException {
    public AnswerNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public AnswerNotFoundException() {
        super("Answer not found!");
    }
}
