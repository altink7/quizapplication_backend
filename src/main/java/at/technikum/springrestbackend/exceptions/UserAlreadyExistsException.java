package at.technikum.springrestbackend.exceptions;

public class UserAlreadyExistsException extends QuizException {
    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public UserAlreadyExistsException() {
        super("User already exists");
    }
}

