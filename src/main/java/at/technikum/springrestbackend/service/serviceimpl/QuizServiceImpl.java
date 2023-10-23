package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.exceptions.QuestionNotFoundException;
import at.technikum.springrestbackend.exceptions.QuizNotFoundException;
import at.technikum.springrestbackend.model.Answer;
import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.model.Quiz;
import at.technikum.springrestbackend.repository.*;
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
    private final QuizDao quizDao;
    private final QuestionDao questionDao;
    private final AnswerOptionDao answerOptionDao;
    private final AnswerDao answerDao;
    private final UserDao userDao;

    @Autowired
    public QuizServiceImpl(QuizDao quizDao, QuestionDao questionDao, AnswerOptionDao answerOptionDao, AnswerDao answerDao, UserDao userDao) {
        this.quizDao = quizDao;
        this.questionDao = questionDao;
        this.answerOptionDao = answerOptionDao;
        this.answerDao = answerDao;
        this.userDao = userDao;
    }


    @Override
    public Quiz getQuizById(Long id) {
        return quizDao.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    @Override
    //FIXME: @ValidateWith(CategoryCustomValidator.class)
    public List<Quiz> getQuizzesByCategory(Category category) {

        return quizDao.findByCategory(category).orElseThrow(QuizNotFoundException::new);
    }

    @Override
    @Transactional
    public Quiz createQuiz(Quiz quiz) {
        Quiz persistedQuiz = quizDao.save(quiz);
        //frontend sends us only the Id of creator, te keep the requests cleaner
        persistedQuiz.setCreator(userDao.findById(quiz.getCreator().getId()).orElse(null));

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
        return quizDao.findById(id).orElseThrow(QuizNotFoundException::new).getQuestions();
    }

    @Override
    public boolean deleteQuiz(Long id) {
        return quizDao.findById(id).map(this::deleteQuiz).orElse(false);
    }

    /**
     * Delete a quiz
     *
     * @param quiz the quiz
     * @return true if deleted
     */
    private boolean deleteQuiz(Quiz quiz) {
        quizDao.delete(quiz);
        return true;
    }
}
