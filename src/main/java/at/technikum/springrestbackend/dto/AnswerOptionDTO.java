package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Answer;
import at.technikum.springrestbackend.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties({"question"})
public class AnswerOptionDTO {
    private Long id;

    @NotNull(message = "Answer cannot be null!")
    private Answer answer;
    private boolean isCorrect;

    @NotNull(message = "Question cannot be null!")
    private Question question;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
