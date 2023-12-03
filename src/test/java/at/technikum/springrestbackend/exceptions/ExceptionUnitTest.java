package at.technikum.springrestbackend.exceptions;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class ExceptionUnitTest {
    @TestFactory
    Stream<DynamicTest> testModelClasses() {
        return classProvider()
                .map(clazz -> dynamicTest("Test constructors for " + clazz.getSimpleName(), () -> {
                    assertDoesNotThrow(() -> clazz.getDeclaredConstructor().newInstance());
                    assertDoesNotThrow(() -> clazz.getDeclaredConstructor(String.class).newInstance("test"));
                }));
    }


    static Stream<Class<?>> classProvider() {
        return Stream.of(
               AnswerNotFoundException.class,
                InvalidPasswordException.class,
                QuestionNotFoundException.class,
                QuizNotFoundException.class,
                UserAlreadyExistsException.class,
                UserNotFoundException.class,
                UserStatisticNotFoundException.class
        );
    }
}
