package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.AnswerOption;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
@JsonIgnoreProperties({"quiz"})
public class QuestionDTO {

    private Long id;

    @NotNull(message = "Question cannot be null!")
    private String question;

    @NotNull(message = "AnswerOption cannot be null!")
    private List<AnswerOption> answerOptions;

    private File file;
}
