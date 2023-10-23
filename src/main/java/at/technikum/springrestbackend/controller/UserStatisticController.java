package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.UserStatisticDTO;
import at.technikum.springrestbackend.model.UserStatistic;
import at.technikum.springrestbackend.service.UserStatisticService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Component
@RequestMapping("/api/users/statistics")
@Validated
public class UserStatisticController extends Controller {

    private final UserStatisticService userStatisticService;

    private final InternalModelMapper mapper;

    @Autowired
    public UserStatisticController(UserStatisticService userStatisticService,
                                   InternalModelMapper mapper) {
        this.userStatisticService = userStatisticService;
        this.mapper = mapper;
    }

    /**
     * GET /api/user/statistics/{userId}
     *
     * @param userId the user id
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserStatisticDTO> getUserStatisticByUserId(@PathVariable @Max(Long.MAX_VALUE) @Min(Long.MIN_VALUE) Long userId) {
        UserStatistic userStatistic = userStatisticService.getUserStatisticByUserId(userId);
        if (userStatistic == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(mapper.mapToDTO(userStatistic, UserStatisticDTO.class));
        }
    }

    /**
     * PUT /api/user/statistics/{userId}
     *
     * @param userId the user id
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserStatisticDTO> updateUserStatisticByUserId(@PathVariable @Max(Long.MAX_VALUE) @Min(Long.MIN_VALUE) Long userId,
                                                                        @RequestParam @Max(Integer.MAX_VALUE) @Min(Integer.MIN_VALUE) int newPoints) {
        UserStatistic userStatistic = userStatisticService.updateUserStatisticByUserId(userId, newPoints);
        if (userStatistic == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(mapper.mapToDTO(userStatistic, UserStatisticDTO.class));
        }
    }
}
