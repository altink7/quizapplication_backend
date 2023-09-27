package at.technikum.springrestbackend.validator.annotation;

import at.technikum.springrestbackend.validator.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * custom annotation for validation
 * @see at.technikum.springrestbackend.validator.Validator
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateWith {

    Class<? extends Validator<?>> value();
}
