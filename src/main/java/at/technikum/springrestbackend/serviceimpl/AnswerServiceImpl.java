package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.model.Answer;
import at.technikum.springrestbackend.repository.AnswerDao;
import at.technikum.springrestbackend.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private AnswerDao answerDao;

    @Override
    public List<Answer> getAllAnswers() {
        return answerDao.findAll();
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    @Override
    public Answer createAnswer(Answer answer) {
        return answerDao.save(answer);
    }

    @Override
    public boolean deleteAnswer(Long id) {
        return answerDao.findById(id).map(this::deleteAnswer)
                .orElse(false);
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        return answerDao.save(answer);
    }

    private Boolean deleteAnswer(Answer answer) {
        answerDao.delete(answer);
        return true;
    }

    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }
}
