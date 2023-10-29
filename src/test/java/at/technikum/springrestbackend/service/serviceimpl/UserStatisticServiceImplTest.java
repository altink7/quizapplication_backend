package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.exceptions.UserStatisticNotFoundException;
import at.technikum.springrestbackend.model.UserStatistic;
import at.technikum.springrestbackend.repository.UserStatisticDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserStatisticServiceImplTest {

    @Mock
    private UserStatisticDao userStatisticDao;

    @InjectMocks
    private UserStatisticServiceImpl userStatisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserStatisticByUserId() {
        Long userId = 1L;
        UserStatistic userStatistic = new UserStatistic();
        when(userStatisticDao.findUserStatisticById(userId)).thenReturn(java.util.Optional.of(userStatistic));

        UserStatistic result = userStatisticService.getUserStatisticByUserId(userId);

        assertThat(result, is(userStatistic));
    }

    @Test
    void testGetUserStatisticByUserIdThrowsException() {
        Long userId = 1L;
        when(userStatisticDao.findUserStatisticById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(UserStatisticNotFoundException.class, () -> userStatisticService.getUserStatisticByUserId(userId));
    }

    @Test
    void testUpdateUserStatisticByUserId() {
        Long userId = 1L;
        int newPoints = 100;
        UserStatistic userStatistic = new UserStatistic();
        when(userStatisticDao.findUserStatisticById(userId)).thenReturn(java.util.Optional.of(userStatistic));
        when(userStatisticDao.save(userStatistic)).thenReturn(userStatistic);

        UserStatistic result = userStatisticService.updateUserStatisticByUserId(userId, newPoints);

        assertThat(result.getPoints(), is(newPoints));
    }

    @Test
    void testUpdateUserStatisticByUserIdThrowsException() {
        Long userId = 1L;
        int newPoints = 100;
        when(userStatisticDao.findUserStatisticById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(UserStatisticNotFoundException.class, () -> userStatisticService.updateUserStatisticByUserId(userId, newPoints));
    }
}
