package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.model.Quiz;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/byCategory")
    public ResponseEntity<Quiz> getQuizByCategory(@RequestParam Category category) {
        Quiz quiz = quizService.getQuizByCategory(category);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(
            @RequestBody User user, @RequestBody Category category, @RequestBody List<Question> questions) {
        Quiz createdQuiz = quizService.createQuiz(user, category, questions);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getAllQuestionsByQuizId(@PathVariable Long id) {
        List<Question> questions = quizService.getAllQuestionsByQuizId(id);
        return ResponseEntity.ok(questions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        boolean deleted = quizService.deleteQuiz(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }
}
