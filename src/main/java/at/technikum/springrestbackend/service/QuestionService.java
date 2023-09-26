package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {

    /**
     * Get a question by category
     * @param category the category
     * @return the question
     */
    Question getQuestionByCategory(Category category);

    /**
     * Get all questions
     * @return  the list of questions
     */
    List<Question> getAllQuestions();

    /**
     * Create a question
     * @param question the question
     * @return the created question
     */
    Question createQuestion(Question question);

    /**
     * Delete a question
     * @param id the id
     * @return true if deleted
     */
    boolean deleteQuestion(Long id);

    /**
     * Update a question
     * @param question the question
     * @return the updated question
     */
    Question updateQuestion(Question question);
}
