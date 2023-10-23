package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.QuestionDTO;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RequestMapping("/api/questions")
@Validated
public class QuestionController extends Controller {
    private final QuestionService questionService;
    private final InternalModelMapper mapper;

    @Autowired
    public QuestionController(QuestionService questionService,
                              InternalModelMapper mapper) {
        this.questionService = questionService;
        this.mapper = mapper;
    }

    /**
     * GET /api/questions/
     *
     * @return all questions
     */
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();

        if (CollectionUtils.isEmpty(questions)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(questions.stream().map(question -> mapper.mapToDTO(question, QuestionDTO.class)).toList());
    }

    @PostMapping("/createQuestion")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO question) {
        //TODO
        return null;
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> updateQuestionById(@PathVariable Long questionId) {
        //TODO
        return null;
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestionById(@PathVariable Long questionId) {
        //TODO
        return null;
    }
}
