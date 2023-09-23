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
        return questionDao.findByCategory(category);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
}
