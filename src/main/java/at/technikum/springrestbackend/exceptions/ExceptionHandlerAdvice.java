package at.technikum.springrestbackend.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.modelmapper.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    //USER Exceptions
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleException(ValidationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    //QUIZ Exceptions
    @ExceptionHandler(QuizException.class)
    public ResponseEntity handleQuizException(QuizException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) //TODO: maybe müssen wir das feiner aufsplitten, weil nicht überall passt NOT_FOUND
                .body(e.getMessage());
    }

    //Validation Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return ResponseEntity.badRequest().body("Field: " + fieldErrors.iterator().next().getField() + "; ErrorMessage: " + fieldErrors.iterator().next().getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid parameter type: " + ex.getName() + ". Value '" + ex.getValue() + "' should be of type " + ex.getRequiredType().getSimpleName();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}