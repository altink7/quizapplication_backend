package at.technikum.springrestbackend.model;

import at.technikum.springrestbackend.testutil.ModelTester;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ModelUnitTest {

    @ParameterizedTest
    @MethodSource("classProvider")
    void testModelClasses(Class<?> clazz) {
        ModelTester.forClass(clazz)
                .test();
    }

    static Stream<Class<?>> classProvider() {
        return Stream.of(
                Answer.class,
                AnswerOption.class,
                Question.class,
                Quiz.class,
                User.class,
                UserStatistic.class,
                Participant.class,
                ProfilPicture.class
        );
    }
}