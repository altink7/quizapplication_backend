package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.dto.AnswerOptionDTO;
import at.technikum.springrestbackend.dto.ParticipantDTO;
import at.technikum.springrestbackend.dto.QuestionDTO;
import at.technikum.springrestbackend.dto.QuizDTO;
import at.technikum.springrestbackend.exceptions.QuestionNotFoundException;
import at.technikum.springrestbackend.exceptions.QuizNotFoundException;
import at.technikum.springrestbackend.model.*;
import at.technikum.springrestbackend.repository.*;
import at.technikum.springrestbackend.service.QuizService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of the QuizService interface
 */

@Service
public class QuizServiceImpl implements QuizService {
    private final UserStatisticDao userStatisticDao;
    private final QuizDao quizDao;
    private final QuestionDao questionDao;
    private final ParticipantDao participantDao;
    private final AnswerOptionDao answerOptionDao;
    private final AnswerDao answerDao;
    private final UserDao userDao;

    @Autowired
    public QuizServiceImpl(UserStatisticDao userStatisticDao, QuizDao quizDao, QuestionDao questionDao, ParticipantDao participantDao, AnswerOptionDao answerOptionDao, AnswerDao answerDao, UserDao userDao) {
        this.userStatisticDao = userStatisticDao;
        this.quizDao = quizDao;
        this.questionDao = questionDao;
        this.participantDao = participantDao;
        this.answerOptionDao = answerOptionDao;
        this.answerDao = answerDao;
        this.userDao = userDao;
    }


    @Override
    public Quiz getQuizById(Long id) {
        return quizDao.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    @Override
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

    @Override
    public Quiz addParticipantToQuiz(Long id, Participant participant) {
        Quiz quiz = quizDao.findById(id).orElseThrow(QuizNotFoundException::new);

        //Wenn Participant registriert ist, dann wird er in die Statistik aufgenommen
        if(participant.getUserId() !=  null) {
            User user = userDao.findById(participant.getUserId()).orElseThrow();
            UserStatistic userStatistic = new UserStatistic();
            userStatistic.setUserId(user.getId());
            userStatistic.getQuizList().add(quiz);
            userStatistic.setPoints(participant.getPoints());

            userStatisticDao.save(userStatistic);
        }

        if(quiz.getParticipants().contains(participant)){
            return quiz;
        }

        participant.setQuiz(quiz);
        participantDao.save(participant);
        quiz.getParticipants().add(participant);
        return quizDao.save(quiz);
    }

    @Override
    public List<ParticipantDTO> sortParticipantsByScoreAndTime(List<ParticipantDTO> participants) {
        participants.sort((o1, o2) -> {
            if (o1.getPoints() == o2.getPoints()) {
                return Double.compare(o1.getParticipantQuizDuration(), o2.getParticipantQuizDuration());
            } else {
                return Integer.compare(o2.getPoints(), o1.getPoints());
            }
        });

        return participants;
    }

    @Override
    public QuizDTO randomizeQuiz(QuizDTO quizDTO) {
        List<QuestionDTO> questions = quizDTO.getQuestions();

        if(questions == null || questions.isEmpty()) {
            return quizDTO;
        }

        Collections.shuffle(questions);

        questions.forEach(question -> {
            List<AnswerOptionDTO> answerOptions = question.getAnswerOptions();
            Collections.shuffle(answerOptions);
            question.setAnswerOptions(answerOptions);
        });

        quizDTO.setQuestions(questions);

        return quizDTO;
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
