package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private QuestionService questionService;

    /**
     * GET /api/question/all
     * @return all questions
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllQuestions() {
        Object questions = questionService.getAllQuestions();

        if (questions == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(questions);
    }

    /**
     * GET /api/question/category/{category}
     * @param category the category
     * @return the question
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Object> getQuestionByCategory(@PathVariable Category category) {
        Object question = questionService.getQuestionByCategory(category);

        if (question != null) {
            return ResponseEntity.ok(question);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}
