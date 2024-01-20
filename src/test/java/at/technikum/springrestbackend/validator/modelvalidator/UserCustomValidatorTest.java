package at.technikum.springrestbackend.validator.modelvalidator;

import at.technikum.springrestbackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ValidationException;
import org.modelmapper.spi.ErrorMessage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserCustomValidatorTest {

    private UserCustomValidator userCustomValidator;

    @BeforeEach
    public void setUp() {
        userCustomValidator = new UserCustomValidator();
    }

    @Test
    public void testValidateValidUser() {
        User user = new User();
        user.setEmail("test@example.com");

        // No exceptions should be thrown for a valid user
        userCustomValidator.validate(user);
    }

    @Test
    public void testValidateNullEmail() {
        User user = new User();

        ValidationException exception = assertThrows(ValidationException.class, () -> userCustomValidator.validate(user));

        List<ErrorMessage> errorMessages = (List<ErrorMessage>) exception.getErrorMessages();
        assertThat(errorMessages.size(), is(1));
        assertThat(errorMessages.get(0).getMessage(), is("Email must not be null"));
    }
}
