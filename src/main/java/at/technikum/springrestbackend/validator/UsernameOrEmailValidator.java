package at.technikum.springrestbackend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameOrEmailValidator implements ConstraintValidator<UsernameOrEmail, String> {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{3,20}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValidEmail(value) || isValidUsername(value);
    }

    private boolean isValidEmail(String value) {
        return value.matches(EMAIL_REGEX);
    }

    private boolean isValidUsername(String value) {
        return value.matches(USERNAME_REGEX);
    }
}
