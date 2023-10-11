package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.exceptions.QuizException;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing users.
 */

@Component
@RequestMapping("/api/users")
public class UserController extends Controller{
    private final UserService userService;
    private final InternalModelMapper mapper;

    public UserController(UserService userService,
                          InternalModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * Get a user by their ID.
     *
     * @param userId The ID of the user.
     * @return A ResponseEntity containing the user's DTO if found, or a "not found" response.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(mapper.mapToDTO(userService.getUserById(userId), UserDTO.class));
        } catch (QuizException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a user by their email.
     *
     * @param email The email address of the user.
     * @return A ResponseEntity containing the user's DTO if found, or a "not found" response.
     */
    @GetMapping("/emails")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        try {
            return ResponseEntity.ok(mapper.mapToDTO(userService.getUserByEmail(email), UserDTO.class));
        } catch (QuizException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a list of all users.
     *
     * @return A ResponseEntity containing a list of user DTOs if found, or a "not found" response.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers().stream()
                .map(user -> mapper.mapToDTO(user, UserDTO.class))
                .toList();

        if (CollectionUtils.isEmpty(userDTOs)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDTOs);
    }

    /**
     * Create a new user.
     *
     * @param userDTO The user DTO to create.
     * @return A ResponseEntity containing the created user's DTO if successful, or a "bad request" response if there is an issue with the request.
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        try {
            User createdUser = userService.createUser(mapper.mapToEntity(userDTO, User.class));
            UserDTO createdUserDTO = mapper.mapToDTO(createdUser, UserDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a user.
     *
     * @param userId  The ID of the user to update.
     * @param userDTO The user DTO to update.
     * @return A ResponseEntity containing the updated user's DTO if successful, or a "not found" response if the user was not found.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            User updatedUser = userService.updateUser(userId, mapper.mapToEntity(userDTO, User.class));
            UserDTO updatedUserDTO = mapper.mapToDTO(updatedUser, UserDTO.class);
            return ResponseEntity.ok(updatedUserDTO);
        } catch (QuizException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a user.
     *
     * @param userId The ID of the user to delete.
     * @return A ResponseEntity containing no content if successful, or a "not found" response if the user was not found.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
