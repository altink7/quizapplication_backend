package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.config.security.UserAuthProvider;
import at.technikum.springrestbackend.dto.CredentialsDTO;
import at.technikum.springrestbackend.dto.TokenResponseDTO;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
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
    public ResponseEntity<TokenResponseDTO> login(@RequestBody CredentialsDTO credentialsDTO) {
        User user = userService.login(credentialsDTO);
        TokenResponseDTO tokenResponse = TokenResponseDTO.builder()
                .token(userAuthProvider.createToken(user))
                .build();
        return ResponseEntity.ok(tokenResponse);
    }

    /**
     * Create a new user.
     *
     * @param userDTO The user DTO to create.
     * @return A ResponseEntity containing the created user's DTO if successful, or a "bad request" response if there is an issue with the request.
     */
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody UserDTO userDTO) {
        User createdUser = userService.register(mapper.mapToEntity(userDTO, User.class));
        TokenResponseDTO tokenResponse = TokenResponseDTO.builder()
                .token(userAuthProvider.createToken(createdUser))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
    }
}
