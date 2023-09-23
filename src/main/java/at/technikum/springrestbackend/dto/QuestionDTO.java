package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.AnswerOption;
import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Quiz;
import lombok.Data;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String question;
    private List<AnswerOption> answerOptions;
    private File file;
    private Quiz quiz;
    private Category category;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
