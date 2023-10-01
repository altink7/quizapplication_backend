package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Quiz;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserStatisticDTO {
    private Long id;
    private Long userId;
    private List<Quiz> quizList;
    private int points;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
