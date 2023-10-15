package at.technikum.springrestbackend.exceptions;

import org.modelmapper.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    //USER Exceptions
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleException(ValidationException e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    //QUIZ Exceptions
    @ExceptionHandler(QuizException.class)
    public ResponseEntity handleQuizException(QuizException e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) //TODO: maybe müssen wir das feiner aufsplitten, weil nicht überall passt NOT_FOUND
                .body(e.getMessage());
    }
}