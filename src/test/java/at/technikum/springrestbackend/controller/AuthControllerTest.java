package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.config.security.UserAuthProvider;
import at.technikum.springrestbackend.dto.CredentialsDTO;
import at.technikum.springrestbackend.dto.TokenResponseDTO;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.model.Gender;
import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private InternalModelMapper mapper;

    @Mock
    private UserAuthProvider userAuthProvider;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        User user = new User();
        when(userService.login(credentialsDTO)).thenReturn(user);
        when(userAuthProvider.createToken(user)).thenReturn("fakeToken");

        ResponseEntity<TokenResponseDTO> response = authController.login(credentialsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("fakeToken", Objects.requireNonNull(response.getBody()).getToken());
        verify(userService, times(1)).login(credentialsDTO);
        verify(userAuthProvider, times(1)).createToken(user);
    }

    @Test
    void testRegister() {
        UserDTO userDTO = getUserDTO();
        User createdUser = new User();
        when(mapper.mapToEntity(userDTO, User.class)).thenReturn(createdUser);
        when(userService.register(createdUser)).thenReturn(createdUser);
        when(userAuthProvider.createToken(createdUser)).thenReturn("fakeToken");

        ResponseEntity<TokenResponseDTO> response = authController.register(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("fakeToken", Objects.requireNonNull(response.getBody()).getToken());
        verify(mapper, times(1)).mapToEntity(userDTO, User.class);
        verify(userService, times(1)).register(createdUser);
        verify(userAuthProvider, times(1)).createToken(createdUser);
    }

    private static UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("email@mail.ccom");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setRole(Role.USER);
        userDTO.setSalutation(Gender.MALE);
        userDTO.setUserStatistic(null);
        userDTO.setPassword(null);
        return userDTO;
    }

    private CredentialsDTO getCredentialsDTO() {
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setEmail("email@mail.com");
        credentialsDTO.setPassword(null);
        return credentialsDTO;
    }
}
