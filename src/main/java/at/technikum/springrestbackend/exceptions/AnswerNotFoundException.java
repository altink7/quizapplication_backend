package at.technikum.springrestbackend.exceptions;

public class AnswerNotFoundException extends QuizExceptions {
    public AnswerNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public AnswerNotFoundException() {
        super("Answer not found!");
    }
}
