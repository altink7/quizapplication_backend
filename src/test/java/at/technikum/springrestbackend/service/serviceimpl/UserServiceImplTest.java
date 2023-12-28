package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.CredentialsDTO;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.exceptions.InvalidPasswordException;
import at.technikum.springrestbackend.exceptions.UserNotFoundException;
import at.technikum.springrestbackend.model.Gender;
import at.technikum.springrestbackend.model.ProfilPicture;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserDao;
import at.technikum.springrestbackend.storage.FileStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private FileStorage fileStorage;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private InternalModelMapper mapper;

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
    void testRegister() {
        User user = new User();
        user.setPassword("notEncodedPassword");
        when(userDao.save(user)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        User result = userService.register(user);

        assertThat(result, is(user));
        // check that the password is encoded
        assertThat(result.getPassword(), is("encodedPassword"));
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
    void testUpdateUserNotNull() {
        Long userId = 1L;
        User originalUser = new User();
        originalUser.setFirstName("firstName");
        originalUser.setLastName("lastName");
        originalUser.setSalutation(Gender.MALE);
        originalUser.setEmail("email@email.at");
        originalUser.setCountry("Austria");
        originalUser.setPassword("secret");

        when(userDao.findById(userId)).thenReturn(Optional.of(originalUser));
        when(userDao.save(originalUser)).thenReturn(originalUser);

        User result = userService.updateUser(userId, originalUser);

        assertThat(result, is(originalUser));
    }

    @Test
    void testUpdateUserThrowsException() {
        Long userId = 1L;
        User updatedUser = new User();
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, updatedUser));
    }


    @Test
    void testUploadProfilePicture() {
        Long userId = 1L;
        User user = new User();
        user.setProfilPicture(new ProfilPicture());
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Test file content".getBytes()
        );

        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));
        when(fileStorage.upload(file)).thenReturn("fileKey");

        assertThrows(UserNotFoundException.class, () -> userService.uploadProfilePicture(file, userId));
    }

    @Test
    void testGetProfilePicture() {
        Long userId = 1L;
        User user = new User();
        ProfilPicture profilPicture = new ProfilPicture();
        profilPicture.setExternalId("fileKey");
        profilPicture.setName("test.txt");
        profilPicture.setContentType("image/jpeg");
        user.setProfilPicture(profilPicture);
        InputStream inputStream = mock(InputStream.class);

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(fileStorage.load("fileKey")).thenReturn(inputStream);

        Map<Resource, MediaType> result = userService.getProfilePicture(userId);

        assertThat(result, hasKey(instanceOf(InputStreamResource.class)));
        assertThat(result, hasValue(MediaType.IMAGE_JPEG));
    }

    @Test
    void testLoginThrowsExceptionWhenUserNotFound() {
        String email = "test@example.com";
        String password = "password";

        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setEmail(email);
        credentialsDTO.setPassword(password.toCharArray());

        when(userDao.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.login(credentialsDTO));
    }

    @Test
    void testLoginThrowsExceptionWhenInvalidPassword() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setPassword(passwordEncoder.encode("wrongPassword"));

        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setEmail(email);
        credentialsDTO.setPassword(password.toCharArray());

        when(userDao.findByEmail(email)).thenReturn(Optional.of(user));

        assertThrows(InvalidPasswordException.class, () -> userService.login(credentialsDTO));
    }

    @Test
    void testGetUpdatedUser() {
        Long userId = 1L;
        User originalUser = new User();
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setCountry("USA");
        Map<String, Object> requestMap = Map.of("user", userDTO, "file", "image/png12:12;12:12base64,encodedfile");

        when(userDao.findById(userId)).thenReturn(Optional.of(originalUser));
        when(fileStorage.upload(any(MultipartFile.class))).thenReturn("fileKey");
        when(userDao.save(originalUser)).thenReturn(originalUser);
        when(mapper.mapToEntity(userDTO, User.class)).thenReturn(originalUser);
        when(objectMapper.convertValue(requestMap.get("user"), UserDTO.class)).thenReturn(userDTO);

        User result = userService.getUpdatedUser(userId, requestMap);

        assertNotNull(result);
    }
}
