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

    /**
     * Update users points by their id
     *
     * @param userId    the user id
     * @param newPoints the updated points
     * @return the user statistic
     */
    UserStatistic updateUserStatisticByUserId(Long userId, int newPoints);
}
