package at.technikum.springrestbackend.validator.annotation;

import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.validator.CustomValidator;
import at.technikum.springrestbackend.validator.modelvalidator.UserCustomValidator;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

import static org.mockito.Mockito.*;

@Component
public class ValidationAspectTest {

    private ValidationAspect validationAspect;

    @Mock
    private JoinPoint joinPoint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validationAspect = new ValidationAspect();
    }

    @Test
    public void testValidate() {
        ValidateWith mockedValidatorAnnotation = Mockito.mock(ValidateWith.class);

        validationAspect.validate(joinPoint, mockedValidatorAnnotation, new Object());

        // verify that the validator annotation is called
        verify(mockedValidatorAnnotation, times(1)).value();
    }
}

