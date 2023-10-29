package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.model.*;
import at.technikum.springrestbackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    private Quiz getQuiz() {
        Quiz quiz = new Quiz();
        quiz.setCreator(new User());
        Question question = new Question();
        question.setQuiz(quiz);
        question.setAnswerOptions(List.of(new AnswerOption()));
        quiz.setQuestions(List.of(question));
        Answer answer = new Answer();
        AnswerOption answerOption = new AnswerOption();
        answerOption.setAnswer(answer);
        answerOption.setQuestion(question);
        question.setAnswerOptions(List.of(answerOption));
        when(quizDao.save(quiz)).thenReturn(quiz);
        return quiz;
    }
}
