package at.technikum.springrestbackend.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameOrEmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameOrEmail {

    String message() default "Not a valid username or email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
