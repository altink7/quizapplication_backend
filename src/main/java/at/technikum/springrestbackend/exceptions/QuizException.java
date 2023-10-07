package at.technikum.springrestbackend.exceptions;

public abstract class QuizException extends RuntimeException {
    public QuizException(String errorMessage) {
        super(errorMessage);
    }
}
