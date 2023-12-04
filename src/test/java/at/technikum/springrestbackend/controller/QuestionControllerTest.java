package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.QuestionDTO;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @Mock
    private InternalModelMapper mapper;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = Collections.singletonList(new Question());
        when(questionService.getAllQuestions()).thenReturn(questions);
        when(mapper.mapToDTO(any(Question.class), eq(QuestionDTO.class))).thenReturn(new QuestionDTO());

        ResponseEntity<List<QuestionDTO>> response = questionController.getAllQuestions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        verify(questionService, times(1)).getAllQuestions();
        verify(mapper, times(1)).mapToDTO(any(Question.class), eq(QuestionDTO.class));
    }

    @Test
    void testGetAllQuestionsNoQuestions() {
        when(questionService.getAllQuestions()).thenReturn(Collections.emptyList());

        ResponseEntity<List<QuestionDTO>> response = questionController.getAllQuestions();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(questionService, times(1)).getAllQuestions();
        verifyNoInteractions(mapper);
    }
}
