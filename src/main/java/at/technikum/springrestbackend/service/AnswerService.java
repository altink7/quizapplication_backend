package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    /**
     * @return all answers
     */
    List<Answer> getAllAnswers();

    /**
     * @return answer by id
     */
    Answer getAnswerById(Long id);

    /**
     * @return created answer
     */
    Answer createAnswer(Answer answer);

    /**
     * @return true if deleted
     */
    boolean deleteAnswer(Long id);

    /**
     * @return updated answer
     */
    Answer updateAnswer(Answer answer);
}
