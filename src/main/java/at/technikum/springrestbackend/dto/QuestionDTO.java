package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.AnswerOption;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
@JsonIgnoreProperties({"quiz"})
public class QuestionDTO {

    private Long id;
    private String question;
    private List<AnswerOption> answerOptions;
    private File file;
}
