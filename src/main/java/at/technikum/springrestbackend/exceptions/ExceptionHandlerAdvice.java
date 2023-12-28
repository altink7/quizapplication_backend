package at.technikum.springrestbackend.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerAdvice {

    //USER Exceptions
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleException(ValidationException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    //QUIZ Exceptions
    @ExceptionHandler(QuizException.class)
    public ResponseEntity<Object>  handleQuizException(QuizException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) //TODO: maybe müssen wir das feiner aufsplitten, weil nicht überall passt NOT_FOUND
                .body(e.getMessage());
    }

    //Validation Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object>  handleValidationException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return ResponseEntity.badRequest().body("Field: " + fieldErrors.iterator().next().getField() + "; ErrorMessage: " + fieldErrors.iterator().next().getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object>  handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid parameter type: " + ex.getName() + ". Value '" + ex.getValue() + "' should be of type " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object>  handleConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}