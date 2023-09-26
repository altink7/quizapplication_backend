package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.mapper.InternalModelMapper;
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
    private InternalModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        try {
            Quiz quiz = quizService.getQuizById(id);
            return ResponseEntity.ok(quiz);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byCategory")
    public ResponseEntity<Quiz> getQuizByCategory(@RequestParam Category category) {
        try{
            Quiz quiz = quizService.getQuizByCategory(category);
            return ResponseEntity.ok(quiz);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        try{
            Quiz quizEntity = mapper.mapToEntity(quiz, Quiz.class);
            Quiz createdQuiz = quizService.createQuiz(quizEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDTO(createdQuiz, Quiz.class));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        if(quizzes.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getAllQuestionsByQuizId(@PathVariable Long id) {
        try {
            List<Question> questions = quizService.getAllQuestionsByQuizId(id);
            return ResponseEntity.ok(questions);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
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

    @Autowired
    public void setMapper(InternalModelMapper mapper) {
        this.mapper = mapper;
    }
}
