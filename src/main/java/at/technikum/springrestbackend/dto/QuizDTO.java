package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"userStatistic"})
public class QuizDTO {

    @NotNull(message = "Creator ID cannot be null!")
    private Long creatorId;

    @NotNull(message = "Category cannot be null!")
    private Category category;

    @NotNull(message = "A quiz needs questions!")
    private List<Question> questions;
}