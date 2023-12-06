package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.exceptions.AnswerNotFoundException;
import at.technikum.springrestbackend.model.Answer;
import at.technikum.springrestbackend.repository.AnswerDao;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnswerServiceImplTest {

    @Mock
    private AnswerDao answerDao;

    @InjectMocks
    private AnswerServiceImpl answerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAnswers() {
        List<Answer> answers = List.of(new Answer(), new Answer());
        when(answerDao.findAll()).thenReturn(answers);

        List<Answer> result = answerService.getAllAnswers();

        assertThat(result, hasSize(2));
        assertThat(result, is(answers));
    }

    @Test
    void testGetAnswerById() {
        Long answerId = 1L;
        Answer answer = new Answer();
        when(answerDao.findById(answerId)).thenReturn(Optional.of(answer));

        Answer result = answerService.getAnswerById(answerId);

        assertThat(result, is(answer));
    }

    @Test
    void testGetAnswerByIdThrowsException() {
        Long answerId = 1L;
        when(answerDao.findById(answerId)).thenReturn(Optional.empty());

        assertThrows(AnswerNotFoundException.class, () -> answerService.getAnswerById(answerId));
    }

    @Test
    void testCreateAnswer() {
        Answer answer = new Answer();
        when(answerDao.save(answer)).thenReturn(answer);

        Answer result = answerService.createAnswer(answer);

        assertThat(result, is(answer));
    }

    @Test
    void testDeleteAnswer() {
        Long answerId = 1L;
        Answer answer = new Answer();
        when(answerDao.findById(answerId)).thenReturn(Optional.of(answer));

        boolean result = answerService.deleteAnswer(answerId);

        verify(answerDao).delete(answer);
        assertTrue(result);
    }

    @Test
    void testDeleteAnswerReturnsFalse() {
        Long answerId = 1L;
        when(answerDao.findById(answerId)).thenReturn(Optional.empty());

        boolean result = answerService.deleteAnswer(answerId);

        assertFalse(result);
    }

    @Test
    void testUpdateAnswer() {
        Answer answer = new Answer();
        when(answerDao.save(answer)).thenReturn(answer);

        Answer result = answerService.updateAnswer(answer);

        assertThat(result, is(answer));
    }
}
