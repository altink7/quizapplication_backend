package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.model.Quiz;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.QuizDao;
import at.technikum.springrestbackend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    private QuizDao quizDao;

    @Override
    public Quiz getQuizById(Long id) {
        return quizDao.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @Override
    public Quiz getQuizByCategory(Category category) {
        return quizDao.findByCategory(category).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @Override
    public Quiz createQuiz(User user, Category category, List<Question> questions) {
        return quizDao.save(getQuizForCategoryUser(user, category, questions));
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
        return quizDao.findById(id).map(quiz -> {
            quizDao.delete(quiz);
            return true;
        }).orElse(false);
    }


    private static Quiz getQuizForCategoryUser(User user, Category category, List<Question> questions) {
        Quiz quiz = new Quiz();
        quiz.setCategory(category);
        quiz.setQuestions(questions);
        quiz.setCreator(user);
        return quiz;
    }

    @Autowired
    public void setQuizDao(QuizDao quizDao) {
        this.quizDao = quizDao;
    }
}
