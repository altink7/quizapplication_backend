package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.QuestionDTO;
import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/api/questions")
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
     * GET /api/questions/all
     *
     * @return all questions
     */
    @GetMapping()
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();

        if (CollectionUtils.isEmpty(questions)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(questions.stream().map(question -> mapper.mapToDTO(question, QuestionDTO.class)).toList());
    }

    /**
     * GET /api/questions/categories/{category}
     *
     * @param category the category
     * @return the question
     */
    @GetMapping("/categories/{category}")
    public ResponseEntity<QuestionDTO> getQuestionByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(mapper.mapToDTO(questionService.getQuestionByCategory(category), QuestionDTO.class));
    }

    //TODO: POST für Admins um Questions zu erstellen?
    //TODO: PUT für Admins um Questions zu ändern?
    //TODO: DELETE für Admins um Questions zu löschen?
    // () -> wird an frontend angepasst & erweitert im laufenden dann
}
