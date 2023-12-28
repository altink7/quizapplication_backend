package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private InternalModelMapper mapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        when(userService.getUserById(userId)).thenReturn(user);
        when(mapper.mapToDTO(user, UserDTO.class)).thenReturn(new UserDTO());

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userService, times(1)).getUserById(userId);
        verify(mapper, times(1)).mapToDTO(user, UserDTO.class);
    }

    @Test
    void testGetUserByIdNotFound() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertNull(response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        when(userService.getUserByEmail(email)).thenReturn(user);
        when(mapper.mapToDTO(user, UserDTO.class)).thenReturn(new UserDTO());

        ResponseEntity<UserDTO> response = userController.getUserByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userService, times(1)).getUserByEmail(email);
        verify(mapper, times(1)).mapToDTO(user, UserDTO.class);
    }

    @Test
    void testGetUserByEmailInvalidEmail() {
        String email = "invalid-email";
        ResponseEntity<UserDTO> response = userController.getUserByEmail(email);
        assertNull(response.getBody());
    }

    @Test
    void testGetUserByEmailNotFound() {
        String email = "test@example.com";
        when(userService.getUserByEmail(email)).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.getUserByEmail(email);

        assertNull(response.getBody());
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Collections.singletonList(new User());
        when(userService.getAllUsers()).thenReturn(users);
        when(mapper.mapToDTO(any(User.class), eq(UserDTO.class))).thenReturn(new UserDTO());

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getAllUsers();
        verify(mapper, times(1)).mapToDTO(any(User.class), eq(UserDTO.class));
    }

    @Test
    void testGetAllUsersNotFound() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getAllUsers();
        verifyNoInteractions(mapper);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        User updatedUser = new User();
        when(mapper.mapToEntity(userDTO, User.class)).thenReturn(updatedUser);
        when(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser);
        when(mapper.mapToDTO(updatedUser, UserDTO.class)).thenReturn(new UserDTO());

        ResponseEntity<UserDTO> response = userController.updateUser(userId, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getUpdatedUser(anyLong(), any());
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        when(mapper.mapToEntity(userDTO, User.class)).thenReturn(new User());
        when(userService.updateUser(userId, new User())).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.updateUser(userId, null);

        assertNull(response.getBody());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(true);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(false);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
}
