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

/**
 * REST Controller for managing quizzes.
 */

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin
public class QuizController {
    private QuizService quizService;
    private InternalModelMapper mapper;

    /**
     * Endpoint to get a quiz by its ID.
     * @param id The ID of the quiz.
     * @return A ResponseEntity containing the quiz if found, or a "not found" response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        try {
            Quiz quiz = quizService.getQuizById(id);
            return ResponseEntity.ok(quiz);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to get a quiz by its category.
     * @param category The category of the quiz.
     * @return A ResponseEntity containing the quiz if found, or a "not found" response.
     */
    @GetMapping("/byCategory")
    public ResponseEntity<Quiz> getQuizByCategory(@RequestParam Category category) {
        try{
            Quiz quiz = quizService.getQuizByCategory(category);
            return ResponseEntity.ok(quiz);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to create a new quiz.
     * @param quiz The quiz to create.
     * @return A ResponseEntity containing the created quiz if successful, or a "not found" response.
     */
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

    /**
     * Endpoint to get all quizzes.
     * @return A ResponseEntity containing a list of quizzes if found, or a "not found" response.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        if(quizzes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    /**
     * Endpoint to get all questions for a quiz by its ID.
     * @param id The ID of the quiz.
     * @return A ResponseEntity containing a list of questions if found, or a "not found" response.
     */
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getAllQuestionsByQuizId(@PathVariable Long id) {
        try {
            List<Question> questions = quizService.getAllQuestionsByQuizId(id);
            return ResponseEntity.ok(questions);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to delete a quiz by its ID.
     * @param id The ID of the quiz to delete.
     * @return A ResponseEntity indicating success or a "not found" response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        boolean deleted = quizService.deleteQuiz(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Set the QuizService.
     * @param quizService The QuizService to set.
     */
    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Set the InternalModelMapper.
     * @param mapper The InternalModelMapper to set.
     */
    @Autowired
    public void setMapper(InternalModelMapper mapper) {
        this.mapper = mapper;
    }
}
