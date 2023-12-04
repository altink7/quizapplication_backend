package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.dto.AnswerOptionDTO;
import at.technikum.springrestbackend.dto.ParticipantDTO;
import at.technikum.springrestbackend.dto.QuestionDTO;
import at.technikum.springrestbackend.dto.QuizDTO;
import at.technikum.springrestbackend.model.*;
import at.technikum.springrestbackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuizServiceImplTest {

    @Mock
    private QuizDao quizDao;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerOptionDao answerOptionDao;

    @Mock
    private AnswerDao answerDao;

    @Mock
    private UserDao userDao;

    @Mock
    private UserStatisticDao userStatisticDao;

    @Mock
    private ParticipantDao participantDao;

    @InjectMocks
    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuizById() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        when(quizDao.findById(quizId)).thenReturn(Optional.of(quiz));

        Quiz result = quizService.getQuizById(quizId);

        assertThat(result, is(quiz));
    }

    @Test
    void testGetQuizzesByCategory() {
        Category category = Category.SPORTS;
        List<Quiz> quizzes = List.of(new Quiz(), new Quiz());
        when(quizDao.findByCategory(category)).thenReturn(Optional.of(quizzes));

        List<Quiz> result = quizService.getQuizzesByCategory(category);

        assertThat(result, hasSize(2));
        assertThat(result, is(quizzes));
    }

    @Test
    void testCreateQuiz() {
        Quiz quiz = getQuiz();

        Quiz result = quizService.createQuiz(quiz);

        assertThat(result, is(quiz));
    }

    @Test
    void testGetAllQuizzes() {
        List<Quiz> quizzes = List.of(new Quiz(), new Quiz());
        when(quizDao.findAll()).thenReturn(quizzes);

        List<Quiz> result = quizService.getAllQuizzes();

        assertThat(result, hasSize(2));
        assertThat(result, is(quizzes));
    }

    @Test
    void testGetAllQuestionsByQuizId() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        List<Question> questions = List.of(new Question(), new Question());
        quiz.setQuestions(questions);
        when(quizDao.findById(quizId)).thenReturn(Optional.of(quiz));

        List<Question> result = quizService.getAllQuestionsByQuizId(quizId);

        assertThat(result, hasSize(2));
        assertThat(result, is(questions));
    }

    @Test
    void testDeleteQuiz() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        when(quizDao.findById(quizId)).thenReturn(Optional.of(quiz));

        boolean result = quizService.deleteQuiz(quizId);

        verify(quizDao).delete(quiz);
        assertTrue(result);
    }

    @Test
    void testDeleteQuizReturnsFalse() {
        Long quizId = 1L;
        when(quizDao.findById(quizId)).thenReturn(Optional.empty());

        boolean result = quizService.deleteQuiz(quizId);

        assertFalse(result);
    }

    @Test
    void testAddParticipantToQuiz() {
        Long quizId = 1L;
        Participant participant = new Participant();

        Quiz quiz = new Quiz();
        quiz.setParticipants(new ArrayList<>());

        when(quizDao.findById(quizId)).thenReturn(Optional.of(quiz));
        when(userDao.findById(any())).thenReturn(Optional.of(new User()));

        quizService.addParticipantToQuiz(quizId, participant);

        Mockito.verify(participantDao).save(participant);
        Mockito.verify(quizDao).save(quiz);
    }

    @Test
    void testAddParticipantToQuizWithUser() {
        Long quizId = 1L;
        Participant participant = new Participant();
        participant.setUserId(1L);

        Quiz quiz = new Quiz();
        quiz.setParticipants(new ArrayList<>());

        when(quizDao.findById(quizId)).thenReturn(Optional.of(quiz));
        when(userDao.findById(any())).thenReturn(Optional.of(new User()));

        quizService.addParticipantToQuiz(quizId, participant);

        Mockito.verify(participantDao).save(participant);
        Mockito.verify(quizDao).save(quiz);
        // should also save a user statistic
        Mockito.verify(userStatisticDao).save(any());
    }

    @Test
    void testSortParticipantsByScoreAndTime() {
        ParticipantDTO participant1 = new ParticipantDTO();
        participant1.setPoints(10);
        participant1.setParticipantQuizDuration(10.0);
        participant1.setNickname("A");
        participant1.setUserId(1L);
        ParticipantDTO participant2 = new ParticipantDTO();
        participant2.setPoints(10);
        participant2.setParticipantQuizDuration(20.0);
        ParticipantDTO participant3 = new ParticipantDTO();
        participant3.setPoints(20);
        participant3.setParticipantQuizDuration(10.0);
        List<ParticipantDTO> participants = Arrays.asList(participant1, participant2, participant3);

        List<ParticipantDTO> result = quizService.sortParticipantsByScoreAndTime(participants);

        // Assert that the list is sorted correctly
        assertThat(result, hasSize(3));
        assertThat(result.get(0), is(participant3));
        assertThat(result.get(1), is(participant1));
        assertThat(result.get(2), is(participant2));
    }

    @Test
    void testRandomizeQuiz() {
        QuizDTO quizDTO = new QuizDTO();
        QuestionDTO question1 = new QuestionDTO();
        question1.setAnswerOptions(Arrays.asList(new AnswerOptionDTO(), new AnswerOptionDTO()));
        QuestionDTO question2 = new QuestionDTO();
        question2.setAnswerOptions(Arrays.asList(new AnswerOptionDTO(), new AnswerOptionDTO()));
        quizDTO.setQuestions(Arrays.asList(question1, question2));

        QuizDTO result = quizService.randomizeQuiz(quizDTO);

        // Assert that questions and answer options are shuffled
        assertThat(result.getQuestions(), hasSize(2));
        assertThat(result.getQuestions().get(0).getAnswerOptions().size(), is(2));
        assertThat(result.getQuestions().get(1).getAnswerOptions().size(), is(2));
    }

    private Quiz getQuiz() {
        Quiz quiz = new Quiz();
        quiz.setCreator(new User());
        quiz.setQuestions(List.of(getQuestion(quiz)));
        quiz.setCategory(Category.SPORTS);
        quiz.setDuration(10);
        quiz.setParticipants(List.of(new Participant()));
        quiz.setStartDate(LocalDate.now().plusDays(7));
        quiz.setCreatedAt(LocalDate.now().atStartOfDay());
        quiz.setUpdatedAt(LocalDate.now().atStartOfDay());
        when(quizDao.save(quiz)).thenReturn(quiz);
        return quiz;
    }

    private Question getQuestion(Quiz quiz) {
        Question question = new Question();
        question.setQuestion("Test");
        question.setQuiz(quiz);
        question.setFile(Mockito.mock(File.class));
        question.setAnswerOptions(List.of(getAnswerOption(question)));
        return question;
    }

    private AnswerOption getAnswerOption(Question question) {
        Answer answer = new Answer();
        answer.setAnswer("Test");
        AnswerOption answerOption = new AnswerOption();
        answerOption.setAnswer(answer);
        answerOption.setCorrect(true);
        answerOption.setQuestion(question);
        return answerOption;
    }
}
