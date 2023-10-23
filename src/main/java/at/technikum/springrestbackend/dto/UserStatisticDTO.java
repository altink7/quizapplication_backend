package at.technikum.springrestbackend.dto;

import lombok.Data;

@Data
public class UserStatisticDTO {
    private Long id;
    private Long userId;
    private int points;
}
