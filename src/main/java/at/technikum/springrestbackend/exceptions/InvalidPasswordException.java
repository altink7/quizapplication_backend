package at.technikum.springrestbackend.exceptions;

public class InvalidPasswordException extends QuizException {
    public InvalidPasswordException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidPasswordException() {
        super("Invalid password!");
    }
}
