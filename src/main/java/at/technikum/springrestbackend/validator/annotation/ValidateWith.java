package at.technikum.springrestbackend.validator.annotation;

import at.technikum.springrestbackend.validator.CustomValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * custom annotation for validation
 *
 * @see CustomValidator
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateWith {

    Class<? extends CustomValidator<?>> value();
}
