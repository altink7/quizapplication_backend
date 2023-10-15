package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.QuestionDTO;
import at.technikum.springrestbackend.dto.QuizDTO;
import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.model.Quiz;
import at.technikum.springrestbackend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing quizzes.
 */

@Component
@RequestMapping("/api/quizzes")
public class QuizController extends Controller {
    private final QuizService quizService;
    private final InternalModelMapper mapper;

    @Autowired
    public QuizController(QuizService quizService,
                          InternalModelMapper mapper) {
        this.quizService = quizService;
        this.mapper = mapper;
    }

    /**
     * Endpoint to get a quiz by its ID.
     *
     * @param id The ID of the quiz.
     * @return A ResponseEntity containing the quiz if found, or a "not found" response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.mapToDTO(quizService.getQuizById(id), QuizDTO.class));
    }

    /**
     * Endpoint to get a quiz by its category.
     *
     * @param category The category of the quiz.
     * @return A ResponseEntity containing the quiz if found, or a "not found" response.
     */
    @GetMapping("/categories/{category}")
    public ResponseEntity<List<QuizDTO>> getQuizByCategory(@PathVariable Category category) {
        List<QuizDTO> quizDTOS = quizService.getQuizzesByCategory(category)
                .stream().map(quiz -> mapper.mapToDTO(quiz, QuizDTO.class))
                .toList();

        return ResponseEntity.ok(quizDTOS);
    }

    /**
     * Endpoint to create a new quiz.
     *
     * @param quiz The quiz to create.
     * @return A ResponseEntity containing the created quiz if successful, or a "not found" response.
     */
    @PostMapping("/createQuiz")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quiz) {
        Quiz quizEntity = mapper.mapToEntity(quiz, Quiz.class);
        Quiz createdQuiz = quizService.createQuiz(quizEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDTO(createdQuiz, QuizDTO.class));
    }

    /**
     * Endpoint to get all quizzes.
     *
     * @return A ResponseEntity containing a list of quizzes if found, or a "not found" response.
     */
    @GetMapping("/all")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        if (CollectionUtils.isEmpty(quizzes)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(quizzes.stream().map(quiz -> mapper.mapToDTO(quiz, QuizDTO.class)).toList());
    }

    /**
     * Endpoint to get all questions for a quiz by its ID.
     *
     * @param id The ID of the quiz.
     * @return A ResponseEntity containing a list of questions if found, or a "not found" response.
     */
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<QuestionDTO>> getAllQuestionsByQuizId(@PathVariable Long id) {
        List<Question> questions = quizService.getAllQuestionsByQuizId(id);
        return ResponseEntity.ok(questions.stream().map(question -> mapper.mapToDTO(question, QuestionDTO.class)).toList());
    }

    /**
     * Endpoint to delete a quiz by its ID.
     *
     * @param id The ID of the quiz to delete.
     * @return A ResponseEntity indicating success or a "not found" response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        return quizService.deleteQuiz(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
