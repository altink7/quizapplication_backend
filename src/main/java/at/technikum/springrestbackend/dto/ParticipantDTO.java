package at.technikum.springrestbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link at.technikum.springrestbackend.model.Participant}
 */
@Data
@JsonIgnoreProperties({"quiz"})
public class ParticipantDTO implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String nickname;
    private int points;
    private double participantQuizDuration;
}