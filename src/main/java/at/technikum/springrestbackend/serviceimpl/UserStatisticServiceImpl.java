package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.model.UserStatistic;
import at.technikum.springrestbackend.repository.UserStatisticDao;
import at.technikum.springrestbackend.service.UserStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatisticServiceImpl implements UserStatisticService {
    private UserStatisticDao userStatisticDao;
    @Override
    public UserStatistic getUserStatisticByUserId(Long userId) {
        return userStatisticDao.findByUserId(userId);
    }

    @Autowired
    public void setUserStatisticDao(UserStatisticDao userStatisticDao) {
        this.userStatisticDao = userStatisticDao;
    }
}
