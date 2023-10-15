package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"userStatistic"})
public class QuizDTO {
    private Long creatorId;
    private Category category;
    private List<Question> questions;
}
