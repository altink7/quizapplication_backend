package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.UserStatisticDTO;
import at.technikum.springrestbackend.model.UserStatistic;
import at.technikum.springrestbackend.service.UserStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequestMapping("/api/users/statistics")
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
    public ResponseEntity<UserStatisticDTO> getUserStatisticByUserId(@PathVariable Long userId) {
        UserStatistic userStatistic = userStatisticService.getUserStatisticByUserId(userId);
        if (userStatistic == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(mapper.mapToDTO(userStatistic, UserStatisticDTO.class));
        }
    }
}
