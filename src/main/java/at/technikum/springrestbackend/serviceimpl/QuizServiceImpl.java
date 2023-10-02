package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.model.*;
import at.technikum.springrestbackend.repository.QuestionDao;
import at.technikum.springrestbackend.repository.AnswerDao;
import at.technikum.springrestbackend.repository.AnswerOptionDao;
import at.technikum.springrestbackend.repository.QuizDao;
import at.technikum.springrestbackend.service.QuizService;
import jakarta.transaction.Transactional;
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

    @Override
    public Quiz getQuizById(Long id) {
        return quizDao.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @Override
    public Quiz getQuizByCategory(Category category) {
        return quizDao.findByCategory(category).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @Override
    @Transactional
    public Quiz createQuiz(Quiz quiz) {
        Quiz persistedQuiz = quizDao.save(quiz);
        quiz.getQuestions().forEach(question -> {

            question.setQuiz(quiz);
            questionDao.save(question);


            question.getAnswerOptions().forEach(answerOption -> {
                Answer answer = answerOption.getAnswer();
                answerDao.save(answer);
                answerOption.setQuestion(question);
                answerOptionDao.save(answerOption);
            });
        });
        return persistedQuiz;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.findAll();
    }

    @Override
    public List<Question> getAllQuestionsByQuizId(Long id) {
        return quizDao.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found")).getQuestions();
    }
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

    @Autowired
    public void setQuizDao(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Autowired
    public void setAnswerOptionDao(AnswerOptionDao answerOptionDao) {
        this.answerOptionDao = answerOptionDao;
    }

    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }


}
