package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.service.UserStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/statistic")
public class UserStatisticController {

    private UserStatisticService userStatisticService;

    /**
     * GET /api/user/statistic/{userId}
     * @param userId the user id
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserStatisticByUserId(@PathVariable String userId) {
        Object userStatistic = userStatisticService.getUserStatisticByUserId(Long.valueOf(userId));
        if (userStatistic == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userStatistic);
        }
    }

    @Autowired
    public void setUserStatisticService(UserStatisticService userStatisticService) {
        this.userStatisticService = userStatisticService;
    }
}
