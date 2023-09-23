package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.model.UserStatistic;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
public class QuizDTO {
    private Long id;
    private User creator;
    private List<User> participants;
    private Category category;
    private List<Question> questions;
    private LocalDate startDate;
    private int duration;
    private UserStatistic userStatistic;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
