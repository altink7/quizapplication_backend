package at.technikum.springrestbackend.validator;

/**
 * object which implements this interface can be used for validation
 */
public interface Validator<T> {

    void validate(T object);
}
