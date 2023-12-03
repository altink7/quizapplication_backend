package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.testutil.ModelTester;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class DtoUnitTest {
    @ParameterizedTest
    @MethodSource("classProvider")
    void testModelClasses(Class<?> clazz) {
        ModelTester.forClass(clazz)
                .test();
    }

    static Stream<Class<?>> classProvider() {
        return Stream.of(
                AnswerOptionDTO.class,
                CredentialsDTO.class,
                QuestionDTO.class,
                QuizDTO.class,
                UserDTO.class,
                UserStatisticDTO.class,
                ParticipantDTO.class,
                TokenResponseDTO.class
        );
    }
}