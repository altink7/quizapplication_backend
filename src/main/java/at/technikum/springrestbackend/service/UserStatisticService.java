package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.UserStatistic;
import org.springframework.stereotype.Service;

@Service
public interface UserStatisticService {

    /**
     * Get a user statistic by user id
     *
     * @param userId the user id
     * @return the user statistic
     */
    UserStatistic getUserStatisticByUserId(Long userId);
}
