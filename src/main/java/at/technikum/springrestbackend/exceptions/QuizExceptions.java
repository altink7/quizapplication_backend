package at.technikum.springrestbackend.exceptions;

public abstract class QuizExceptions extends RuntimeException {
    public QuizExceptions(String errorMessage) {
        super(errorMessage);
    }
}
