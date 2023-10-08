package at.technikum.springrestbackend.exceptions;

public class UserStatisticNotFoundException extends QuizException {
    public UserStatisticNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public UserStatisticNotFoundException() {
        super("User Statistic not found!");
    }
}
