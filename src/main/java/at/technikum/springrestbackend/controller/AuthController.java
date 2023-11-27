package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.config.security.UserAuthProvider;
import at.technikum.springrestbackend.dto.CredentialsDTO;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/api/auth")
public class AuthController extends Controller {

    private final UserService userService;
    private final InternalModelMapper mapper;
    private final UserAuthProvider userAuthProvider;

    public AuthController(UserService userService, InternalModelMapper mapper, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.mapper = mapper;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialsDTO credentialsDTO) {
        User user = userService.login(credentialsDTO);
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .token(userAuthProvider.createToken(user))
                .salutation(user.getSalutation())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword().toCharArray())
                .country(user.getCountry())
                .role(user.getRole())
                .userStatistic(user.getUserStatistic())
                .build();
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Create a new user.
     *
     * @param userDTO The user DTO to create.
     * @return A ResponseEntity containing the created user's DTO if successful, or a "bad request" response if there is an issue with the request.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) {
        User createdUser = userService.register(mapper.mapToEntity(userDTO, User.class));
        UserDTO createdUserDTO = UserDTO.builder()
                .id(createdUser.getId())
                .token(userAuthProvider.createToken(createdUser))
                .salutation(createdUser.getSalutation())
                .firstName(createdUser.getFirstName())
                .lastName(createdUser.getLastName())
                .email(createdUser.getEmail())
                .password(createdUser.getPassword().toCharArray())
                .country(createdUser.getCountry())
                .role(createdUser.getRole())
                .userStatistic(createdUser.getUserStatistic())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }
}
