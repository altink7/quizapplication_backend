package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.exceptions.AnswerNotFoundException;
import at.technikum.springrestbackend.model.Answer;
import at.technikum.springrestbackend.repository.AnswerDao;
import at.technikum.springrestbackend.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the AnswerService interface
 */

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerDao answerDao;

    @Autowired
    public AnswerServiceImpl(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }


    @Override
    public List<Answer> getAllAnswers() {
        return answerDao.findAll();
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerDao.findById(id).orElseThrow(AnswerNotFoundException::new);
    }

    @Override
    public Answer createAnswer(Answer answer) {
        return answerDao.save(answer);
    }

    @Override
    public boolean deleteAnswer(Long id) {
        return answerDao.findById(id).map(answer -> {
            answerDao.delete(answer);
            return true;
        }).orElse(false);
    }


    @Override
    public Answer updateAnswer(Answer answer) {
        return answerDao.save(answer);
    }
}
