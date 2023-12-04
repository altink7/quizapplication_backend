package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.UserStatisticDTO;
import at.technikum.springrestbackend.model.UserStatistic;
import at.technikum.springrestbackend.service.UserStatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserStatisticControllerTest {

    @Mock
    private UserStatisticService userStatisticService;

    @Mock
    private InternalModelMapper mapper;

    @InjectMocks
    private UserStatisticController userStatisticController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserStatisticByUserId() {
        Long userId = 1L;
        UserStatistic userStatistic = new UserStatistic();
        when(userStatisticService.getUserStatisticByUserId(userId)).thenReturn(userStatistic);
        when(mapper.mapToDTO(userStatistic, UserStatisticDTO.class)).thenReturn(new UserStatisticDTO());

        ResponseEntity<UserStatisticDTO> response = userStatisticController.getUserStatisticByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userStatisticService, times(1)).getUserStatisticByUserId(userId);
        verify(mapper, times(1)).mapToDTO(userStatistic, UserStatisticDTO.class);
    }

    @Test
    void testGetUserStatisticByUserIdNotFound() {
        Long userId = 1L;
        when(userStatisticService.getUserStatisticByUserId(userId)).thenReturn(null);

        ResponseEntity<UserStatisticDTO> response = userStatisticController.getUserStatisticByUserId(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userStatisticService, times(1)).getUserStatisticByUserId(userId);
        verifyNoInteractions(mapper);
    }

    @Test
    void testUpdateUserStatisticByUserId() {
        Long userId = 1L;
        int newPoints = 50;
        UserStatistic updatedUserStatistic = new UserStatistic();
        when(userStatisticService.updateUserStatisticByUserId(userId, newPoints)).thenReturn(updatedUserStatistic);
        when(mapper.mapToDTO(updatedUserStatistic, UserStatisticDTO.class)).thenReturn(new UserStatisticDTO());

        ResponseEntity<UserStatisticDTO> response = userStatisticController.updateUserStatisticByUserId(userId, newPoints);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userStatisticService, times(1)).updateUserStatisticByUserId(userId, newPoints);
        verify(mapper, times(1)).mapToDTO(updatedUserStatistic, UserStatisticDTO.class);
    }

    @Test
    void testUpdateUserStatisticByUserIdNotFound() {
        Long userId = 1L;
        int newPoints = 50;
        when(userStatisticService.updateUserStatisticByUserId(userId, newPoints)).thenReturn(null);

        ResponseEntity<UserStatisticDTO> response = userStatisticController.updateUserStatisticByUserId(userId, newPoints);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userStatisticService, times(1)).updateUserStatisticByUserId(userId, newPoints);
        verifyNoInteractions(mapper);
    }
}
