package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Quiz;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserStatisticDTO {
    private Long id;
    private Long userId;
    private List<QuizDTO> quizList;
    private int points;
}
