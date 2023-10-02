package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.exceptions.QuestionNotFoundException;
import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.repository.QuestionDao;
import at.technikum.springrestbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the QuestionService interface
 */

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question getQuestionByCategory(Category category) {
        return questionDao.findByCategory(category).orElseThrow(QuestionNotFoundException::new);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    @Override
    public Question createQuestion(Question question) {
        return questionDao.save(question);
    }

    @Override
    public boolean deleteQuestion(Long id) {
        return questionDao.findById(id).map(question -> {
            questionDao.delete(question);
            return true;
        }).orElse(false);
    }

    @Override
    public Question updateQuestion(Question question) {
        return questionDao.save(question);
    }
}

