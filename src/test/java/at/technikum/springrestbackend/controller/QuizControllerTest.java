package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.ParticipantDTO;
import at.technikum.springrestbackend.dto.QuestionDTO;
import at.technikum.springrestbackend.dto.QuizDTO;
import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Participant;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.model.Quiz;
import at.technikum.springrestbackend.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @Mock
    private InternalModelMapper mapper;

    @InjectMocks
    private QuizController quizController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuizById() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        when(quizService.getQuizById(quizId)).thenReturn(quiz);
        when(mapper.mapToDTO(quiz, QuizDTO.class)).thenReturn(new QuizDTO());

        ResponseEntity<QuizDTO> response = quizController.getQuizById(quizId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(quizService, times(1)).getQuizById(quizId);
        verify(mapper, times(1)).mapToDTO(quiz, QuizDTO.class);
    }

    @Test
    void testGetQuizByIdNotFound() {
        Long quizId = 1L;
        when(quizService.getQuizById(quizId)).thenReturn(null);

        ResponseEntity<QuizDTO> response = quizController.getQuizById(quizId);

        assertNull(response.getBody());
        verify(quizService, times(1)).getQuizById(quizId);
    }

    @Test
    void testGetQuizByCategory() {
        Category category = Category.SCIENCE;
        List<Quiz> quizzes = Collections.singletonList(new Quiz());
        when(quizService.getQuizzesByCategory(category)).thenReturn(quizzes);
        when(mapper.mapToDTO(any(Quiz.class), eq(QuizDTO.class))).thenReturn(new QuizDTO());

        ResponseEntity<List<QuizDTO>> response = quizController.getQuizByCategory(category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(quizService, times(1)).getQuizzesByCategory(category);
        verify(mapper, times(1)).mapToDTO(any(Quiz.class), eq(QuizDTO.class));
    }

    @Test
    void testGetQuizByCategoryNotFound() {
        Category category = Category.SCIENCE;
        when(quizService.getQuizzesByCategory(category)).thenReturn(Collections.emptyList());

        quizController.getQuizByCategory(category);

        verify(quizService, times(1)).getQuizzesByCategory(category);
        verifyNoInteractions(mapper);
    }

    @Test
    void testCreateQuiz() {
        QuizDTO quizDTO = new QuizDTO();
        Quiz quizEntity = new Quiz();
        when(mapper.mapToEntity(quizDTO, Quiz.class)).thenReturn(quizEntity);
        when(quizService.createQuiz(quizEntity)).thenReturn(quizEntity);
        when(mapper.mapToDTO(quizEntity, QuizDTO.class)).thenReturn(new QuizDTO());

        ResponseEntity<QuizDTO> response = quizController.createQuiz(quizDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(mapper, times(1)).mapToEntity(quizDTO, Quiz.class);
        verify(quizService, times(1)).createQuiz(quizEntity);
        verify(mapper, times(1)).mapToDTO(quizEntity, QuizDTO.class);
    }

    @Test
    void testGetAllQuizzes() {
        List<Quiz> quizzes = Collections.singletonList(new Quiz());
        when(quizService.getAllQuizzes()).thenReturn(quizzes);
        when(mapper.mapToDTO(any(Quiz.class), eq(QuizDTO.class))).thenReturn(new QuizDTO());

        ResponseEntity<List<QuizDTO>> response = quizController.getAllQuizzes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(quizService, times(1)).getAllQuizzes();
        verify(mapper, times(1)).mapToDTO(any(Quiz.class), eq(QuizDTO.class));
    }

    @Test
    void testGetAllQuizzesNotFound() {
        when(quizService.getAllQuizzes()).thenReturn(Collections.emptyList());

        ResponseEntity<List<QuizDTO>> response = quizController.getAllQuizzes();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(quizService, times(1)).getAllQuizzes();
        verifyNoInteractions(mapper);
    }

    @Test
    void testGetAllQuestionsByQuizId() {
        Long quizId = 1L;
        List<Question> questions = Collections.singletonList(new Question());
        when(quizService.getAllQuestionsByQuizId(quizId)).thenReturn(questions);
        when(mapper.mapToDTO(any(Question.class), eq(QuestionDTO.class))).thenReturn(new QuestionDTO());

        ResponseEntity<List<QuestionDTO>> response = quizController.getAllQuestionsByQuizId(quizId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(quizService, times(1)).getAllQuestionsByQuizId(quizId);
        verify(mapper, times(1)).mapToDTO(any(Question.class), eq(QuestionDTO.class));
    }

    @Test
    void testDeleteQuiz() {
        Long quizId = 1L;
        when(quizService.deleteQuiz(quizId)).thenReturn(true);

        ResponseEntity<Void> response = quizController.deleteQuiz(quizId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(quizService, times(1)).deleteQuiz(quizId);
    }

    @Test
    void testDeleteQuizNotFound() {
        Long quizId = 1L;
        when(quizService.deleteQuiz(quizId)).thenReturn(false);

        ResponseEntity<Void> response = quizController.deleteQuiz(quizId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(quizService, times(1)).deleteQuiz(quizId);
    }

    @Test
    void testAddParticipantToQuiz() {
        Long quizId = 1L;
        ParticipantDTO participantDTO = new ParticipantDTO();
        Participant participantEntity = new Participant();
        Quiz quiz = new Quiz();
        when(mapper.mapToEntity(participantDTO, Participant.class)).thenReturn(participantEntity);
        when(quizService.addParticipantToQuiz(quizId, participantEntity)).thenReturn(quiz);
        when(mapper.mapToDTO(quiz, QuizDTO.class)).thenReturn(new QuizDTO());

        ResponseEntity<QuizDTO> response = quizController.addParticipantToQuiz(quizId, participantDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mapper, times(1)).mapToEntity(participantDTO, Participant.class);
        verify(quizService, times(1)).addParticipantToQuiz(quizId, participantEntity);
        verify(mapper, times(1)).mapToDTO(quiz, QuizDTO.class);
    }

    @Test
    void testGetAllParticipantsByQuizId() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        List<Participant> participants = Collections.singletonList(new Participant());
        quiz.setParticipants(participants);
        when(quizService.getQuizById(quizId)).thenReturn(quiz);
        when(mapper.mapToDTO(any(Participant.class), eq(ParticipantDTO.class))).thenReturn(new ParticipantDTO());

        ResponseEntity<List<ParticipantDTO>> response = quizController.getAllParticipantsByQuizId(quizId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(quizService, times(1)).getQuizById(quizId);
        verify(mapper, times(1)).mapToDTO(any(Participant.class), eq(ParticipantDTO.class));
    }

    @Test
    void testGetAllQuizzesByCreatorId() {
        Long creatorId = 1L;
        List<Quiz> quizzes = Collections.singletonList(new Quiz());
        when(quizService.getAllQuizzesByCreatorId(creatorId)).thenReturn(quizzes);
        when(mapper.mapToDTO(any(Quiz.class), eq(QuizDTO.class))).thenReturn(new QuizDTO());

        ResponseEntity<List<QuizDTO>> response = quizController.getAllQuizzesByCreatorId(creatorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(quizService, times(1)).getAllQuizzesByCreatorId(creatorId);
        verify(mapper, times(1)).mapToDTO(any(Quiz.class), eq(QuizDTO.class));
    }

    @Test
    void testGetAllQuizzesByCreatorIdAndQuizId404Error() {
        Long creatorId = 1L;
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        when(quizService.getQuizById(quizId)).thenReturn(quiz);
        when(mapper.mapToDTO(quiz, QuizDTO.class)).thenReturn(new QuizDTO());

        quizController.getAllQuizzesByCreatorIdAndQuizId(quizId, creatorId);

        verify(quizService, times(0)).getQuizById(quizId);
        verify(mapper, times(0)).mapToDTO(quiz, QuizDTO.class);
    }
}
