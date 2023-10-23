package at.technikum.springrestbackend.validator.annotation;

import at.technikum.springrestbackend.validator.CustomValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * Aspect for validation
 * it runs validate method of the validator class
 *
 * @see CustomValidator
 */
@Aspect
@Component
public class ValidationAspect {

    @AfterReturning(pointcut = "@annotation(validateWith)", returning = "result")
    public void validate(JoinPoint joinPoint, ValidateWith validateWith, Object result) {
        Class<? extends CustomValidator<?>> validatorClass = validateWith.value();
        if (result != null && validatorClass != null) {
            CustomValidator<Object> customValidator = instantiateValidator(validatorClass);
            customValidator.validate(result);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> CustomValidator<T> instantiateValidator(Class<? extends CustomValidator<?>> validatorClass) {
        try {
            return (CustomValidator<T>) validatorClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Error instantiating validator", e);

        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
