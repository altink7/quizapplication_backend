package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.repository.QuestionDao;
import at.technikum.springrestbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionDao questionDao;

    @Override
    public Question getQuestionByCategory(Category category) {
        return questionDao.findByCategory(category).orElseThrow(() -> new RuntimeException("Question not found"));
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
        return questionDao.findById(id).map(this::deleteQuestion).orElse(false);
    }

    @Override
    public Question updateQuestion(Question question) {
        return questionDao.save(question);
    }

    private Boolean deleteQuestion(Question question) {
        questionDao.delete(question);
        return true;
    }

    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
}
