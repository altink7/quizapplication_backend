package at.technikum.springrestbackend.model;

import at.technikum.springrestbackend.testutil.ModelTester;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ModelUnitTest {

    @ParameterizedTest
    @MethodSource("classProvider")
    void testModelClasses(Class<?> clazz) {
        ModelTester.forClass(clazz)
                .test();
    }

    static java.util.stream.Stream<Class<?>> classProvider() {
        return java.util.stream.Stream.of(
                Answer.class,
                AnswerOption.class,
                Question.class,
                Quiz.class,
                User.class,
                UserStatistic.class
        );
    }
}