package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.exceptions.UserNotFoundException;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(new User(), new User());
        when(userDao.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result, hasSize(2));
        assertThat(result, is(users));
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        when(userDao.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertThat(result, is(user));
    }

    @Test
    void testGetUserByIdThrowsException() {
        Long userId = 1L;
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testGetUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        when(userDao.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail(email);

        assertThat(result, is(user));
    }

    @Test
    void testGetUserByEmailThrowsException() {
        String email = "test@example.com";
        when(userDao.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(email));
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User user = new User();
        when(userDao.findById(userId)).thenReturn(Optional.of(user));

        boolean result = userService.deleteUser(userId);

        verify(userDao).delete(user);
        assertTrue(result);
    }

    @Test
    void testDeleteUserReturnsFalse() {
        Long userId = 1L;
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        boolean result = userService.deleteUser(userId);

        assertFalse(result);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        when(userDao.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertThat(result, is(user));
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User originalUser = new User();
        User updatedUser = new User();
        when(userDao.findById(userId)).thenReturn(Optional.of(originalUser));
        when(userDao.save(originalUser)).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertThat(result, is(updatedUser));
    }

    @Test
    void testUpdateUserThrowsException() {
        Long userId = 1L;
        User updatedUser = new User();
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, updatedUser));
    }
}
