package at.technikum.springrestbackend.exceptions;

public class UserStatisticNotFoundException extends QuizExceptions {
    public UserStatisticNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public UserStatisticNotFoundException() {
        super("User Statistic not found!");
    }
}
