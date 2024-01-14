package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import at.technikum.springrestbackend.validator.UsernameOrEmail;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing users.
 */

@Component
@RequestMapping("/api/users")
@Validated
public class UserController extends Controller {
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable @Max(Long.MAX_VALUE) @Min(Long.MIN_VALUE) Long userId) {
        return ResponseEntity.ok(mapper.mapToDTO(userService.getUserById(userId), UserDTO.class));
    }

    /**
     * Get a user by their email.
     *
     * @param emailOrUsername The email address of the user.
     * @return A ResponseEntity containing the user's DTO if found, or a "not found" response.
     */
    @GetMapping("/emails/{emailOrUsername}")
    public ResponseEntity<UserDTO> getUserByEmailOrUsername(@PathVariable @NotNull(message = "Email cannot be null!")
                                                            @UsernameOrEmail(message = "Not a valid email or username!") String emailOrUsername) {
        return ResponseEntity.ok(mapper.mapToDTO(userService.getUserByEmailOrUsername(emailOrUsername), UserDTO.class));
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

        return CollectionUtils.isEmpty(userDTOs) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userDTOs);
    }

    /**
     * Delete a user.
     *
     * @param userId The ID of the user to delete.
     * @return A ResponseEntity containing no content if successful, or a "not found" response if the user was not found.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Max(Long.MAX_VALUE) @Min(Long.MIN_VALUE) Long userId) {
        return userService.deleteUser(userId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();//204
    }


    /**
     * Update a user.
     *
     * @param userId  The ID of the user to update.
     * @return A ResponseEntity containing the updated user's DTO if successful, or a "not found" response if the user was not found.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable @Max(Long.MAX_VALUE) @Min(Long.MIN_VALUE) Long userId,
            @RequestBody Map<String, Object> requestMap) {
        User updatedUser = userService.getUpdatedUser(userId, requestMap);
        UserDTO updatedUserDTO = mapper.mapToDTO(updatedUser, UserDTO.class);
        return ResponseEntity.ok(updatedUserDTO);
    }

    /**
     * Get a profile picture for a user.
     * @param userId The ID of the user to get the profile picture for.
     * @return A ResponseEntity containing the profile picture if successful, or a "not found" response if the user was not found.
     */
    @GetMapping("/{userId}/profile-picture")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable @Max(Long.MAX_VALUE) @Min(Long.MIN_VALUE) Long userId) {
        Map<Resource, MediaType> profilePicture = userService.getProfilePicture(userId);
        MediaType mediaType = profilePicture.values().stream().findFirst().orElse(null);
        Resource resource = profilePicture.keySet().stream().findFirst().orElse(null);

        if (mediaType == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }
}
