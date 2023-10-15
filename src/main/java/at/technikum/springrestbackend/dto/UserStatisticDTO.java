package at.technikum.springrestbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserStatisticDTO {
    private Long id;
    private Long userId;
    private List<QuizDTO> quizList;
    private int points;
}
