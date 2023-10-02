package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

    /**
     * Get a quiz by id
     *
     * @param id the quiz id
     * @return the quiz
     */
    Quiz getQuizById(Long id);

    /**
     * Get a quiz by category
     *
     * @param category the category
     * @return the quiz
     */
    Quiz getQuizByCategory(Category category);

    /**
     * Create a new quiz
     *
     * @param quiz quiz
     * @return the created quiz
     */
    Quiz createQuiz(Quiz quiz);

    /**
     * Get all quizzes
     *
     * @return the list of quizzes
     */
    List<Quiz> getAllQuizzes();

    /**
     * Get all questions by quiz id
     *
     * @param id the quiz id
     * @return the list of questions
     */
    List<Question> getAllQuestionsByQuizId(Long id);

    /**
     * Delete a quiz by id
     *
     * @param id the quiz id
     * @return true if the quiz was deleted, false otherwise
     */
    boolean deleteQuiz(Long id);
}
