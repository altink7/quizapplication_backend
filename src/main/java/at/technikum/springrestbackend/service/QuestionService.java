package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {

    /**
     * Get all questions
     *
     * @return the list of questions
     */
    List<Question> getAllQuestions();

    /**
     * Create a question
     *
     * @param question the question
     * @return the created question
     */
    Question createQuestion(Question question);

    /**
     * Delete a question
     *
     * @param id the id
     * @return true if deleted
     */
    boolean deleteQuestion(Long id);

    /**
     * Update a question
     *
     * @param question the question
     * @return the updated question
     */
    Question updateQuestion(Question question);
}
