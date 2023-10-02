package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.exceptions.UserStatisticNotFoundException;
import at.technikum.springrestbackend.model.UserStatistic;
import at.technikum.springrestbackend.repository.UserStatisticDao;
import at.technikum.springrestbackend.service.UserStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatisticServiceImpl implements UserStatisticService {
    private final UserStatisticDao userStatisticDao;

    @Autowired
    public UserStatisticServiceImpl(UserStatisticDao userStatisticDao) {
        this.userStatisticDao = userStatisticDao;
    }

    @Override
    public UserStatistic getUserStatisticByUserId(Long userId) {
        return userStatisticDao.findByUserId(userId).orElseThrow(UserStatisticNotFoundException::new);
    }

}
