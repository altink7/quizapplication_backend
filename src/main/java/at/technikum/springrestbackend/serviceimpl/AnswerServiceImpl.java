package at.technikum.springrestbackend.serviceimpl;

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
    private AnswerDao answerDao;

    /**
     * @return all answers
     */
    @Override
    public List<Answer> getAllAnswers() {
        return answerDao.findAll();
    }

    /**
     * @param id
     * @return answer by id
     */
    @Override
    public Answer getAnswerById(Long id) {
        return answerDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    /**
     * @param answer
     * @return created answer
     */
    @Override
    public Answer createAnswer(Answer answer) {
        return answerDao.save(answer);
    }

    /**
     * @param id
     * @return true if deleted
     */
    @Override
    public boolean deleteAnswer(Long id) {
        return answerDao.findById(id).map(this::deleteAnswer)
                .orElse(false);
    }

    /**
     * @param answer
     * @return updated answer
     */
    @Override
    public Answer updateAnswer(Answer answer) {
        return answerDao.save(answer);
    }

    /**
     * @param answer
     * @return true if deleted
     */
    private Boolean deleteAnswer(Answer answer) {
        answerDao.delete(answer);
        return true;
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
