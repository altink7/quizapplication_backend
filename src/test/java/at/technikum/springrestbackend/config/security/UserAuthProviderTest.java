package at.technikum.springrestbackend.config.security;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserDao;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserAuthProviderTest {

    @Mock
    private UserDao userDao;

    @Mock
    private InternalModelMapper mapper;

    private UserAuthProvider userAuthProvider;

    private final String secretKey = "secret-key";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userAuthProvider = new UserAuthProvider(userDao, mapper);
        ReflectionTestUtils.setField(userAuthProvider, "secretKey", secretKey);

        userAuthProvider.init();
    }

    @Test
    void createToken() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole(Role.USER);

        String token = userAuthProvider.createToken(user);

        assertNotNull(token);
    }

    @Test
    void validateTokenStrongly_UserNotFound() {
        String token = createToken(getUserDTO());

        when(userDao.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(SignatureVerificationException.class, () -> userAuthProvider.validateTokenStrongly(token));
    }

    private String createToken(UserDTO userDTO) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(userDTO.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", userDTO.getFirstName())
                .withClaim("lastName", userDTO.getLastName())
                .withClaim("role", userDTO.getRole().toString())
                .sign(Algorithm.HMAC256(secretKey));
    }

    private UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("email@mail.com");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setRole(Role.USER);
        return userDTO;
    }
}
