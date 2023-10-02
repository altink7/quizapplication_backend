package at.technikum.springrestbackend.exceptions;

public class UserNotFoundException extends QuizExceptions {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public UserNotFoundException() {
        super("User not found!");
    }
}
