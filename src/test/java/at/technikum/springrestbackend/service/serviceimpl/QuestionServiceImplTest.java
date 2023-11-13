package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.repository.QuestionDao;
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

public class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = List.of(new Question(), new Question());
        when(questionDao.findAll()).thenReturn(questions);

        List<Question> result = questionService.getAllQuestions();

        assertThat(result, hasSize(2));
        assertThat(result, is(questions));
    }

    @Test
    void testCreateQuestion() {
        Question question = new Question();
        when(questionDao.save(question)).thenReturn(question);

        Question result = questionService.createQuestion(question);

        assertThat(result, is(question));
    }

    @Test
    void testDeleteQuestion() {
        Long questionId = 1L;
        Question question = new Question();
        when(questionDao.findById(questionId)).thenReturn(Optional.of(question));

        boolean result = questionService.deleteQuestion(questionId);

        verify(questionDao).delete(question);
        assertTrue(result);
    }

    @Test
    void testDeleteQuestionReturnsFalse() {
        Long questionId = 1L;
        when(questionDao.findById(questionId)).thenReturn(Optional.empty());

        boolean result = questionService.deleteQuestion(questionId);

        assertFalse(result);
    }

    @Test
    void testUpdateQuestion() {
        Question question = new Question();
        when(questionDao.save(question)).thenReturn(question);

        Question result = questionService.updateQuestion(question);

        assertThat(result, is(question));
    }
}