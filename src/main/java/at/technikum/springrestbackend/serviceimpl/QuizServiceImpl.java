package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.model.*;
import at.technikum.springrestbackend.repository.QuestionDao;
import at.technikum.springrestbackend.repository.AnswerDao;
import at.technikum.springrestbackend.repository.AnswerOptionDao;
import at.technikum.springrestbackend.repository.QuizDao;
import at.technikum.springrestbackend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the QuizService interface
 */

@Service
public class QuizServiceImpl implements QuizService {
    private QuizDao quizDao;
    private QuestionDao questionDao;
    private AnswerOptionDao answerOptionDao;
    private AnswerDao answerDao;

    /**
     * Get a quiz by id
     * @param id the id
     * @return the quiz
     */
    @Override
    public Quiz getQuizById(Long id) {
        return quizDao.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    /**
     * Get a quiz by category
     * @param category the category
     * @return the quiz
     */
    @Override
    public Quiz getQuizByCategory(Category category) {
        return quizDao.findByCategory(category).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    /**
     * Create a new quiz
     * @param quiz the quiz
     * @return the created quiz
     */
    @Override
    public Quiz createQuiz(Quiz quiz) {
        Quiz persistedQuiz = new Quiz();
        return quizDao.save(quiz);
    }

    /**
     * Get all quizzes
     * @return the list of quizzes
     */
    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.findAll();
    }

    /**
     * Get all questions by quiz id
     * @param id the quiz id
     * @return the list of questions
     */
    @Override
    public List<Question> getAllQuestionsByQuizId(Long id) {
        return quizDao.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found")).getQuestions();
    }

    /**
     * Delete a quiz by id
     * @param id the quiz id
     * @return true if the quiz was deleted, false otherwise
     */
    @Override
    public boolean deleteQuiz(Long id) {
        return quizDao.findById(id).map(this::deleteQuiz).orElse(false);
    }

    /**
     * Delete a quiz
     * @param quiz the quiz
     * @return true if deleted
     */
    private Boolean deleteQuiz(Quiz quiz) {
        quizDao.delete(quiz);
        return true;
    }

    /**
     * Sets the QuizDao implementation to be used by this service.
     * @param quizDao The QuizDao implementation.
     */
    @Autowired
    public void setQuizDao(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    /**
     * Sets the QuestionDao implementation to be used by this service.
     * @param questionDao The QuestionDao implementation.
     */
    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    /**
     * Sets the AnswerOptionDao implementation to be used by this service.
     * @param answerOptionDao The AnswerOptionDao implementation.
     */
    @Autowired
    public void setAnswerOptionDao(AnswerOptionDao answerOptionDao) {
        this.answerOptionDao = answerOptionDao;
    }

    /**
     * Sets the AnswerDao implementation to be used by this service.
     * @param answerDao The AnswerDao implementation.
     */
    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }


}
