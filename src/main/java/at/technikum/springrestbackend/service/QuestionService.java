package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {

    /**
     * Get a question by category
     * @param category the category
     * @return the question
     */
    Question getQuestionByCategory(Category category);

    /**
     * Get all questions
     * @return  the list of questions
     */
    List<Question> getAllQuestions();
}
