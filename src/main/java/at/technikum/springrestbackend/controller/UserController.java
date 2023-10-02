package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.mapper.InternalModelMapper;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing users.
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private UserService userService;
    private InternalModelMapper mapper;

    /**
     * Get a user by their ID.
     * @param userId The ID of the user.
     * @return A ResponseEntity containing the user's DTO if found, or a "not found" response.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        try{
            UserDTO userDTO = mapper.mapToDTO(userService.getUserById(userId), UserDTO.class);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a user by their email.
     * @param email The email address of the user.
     * @return A ResponseEntity containing the user's DTO if found, or a "not found" response.
     */
    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        try{
            UserDTO userDTO = mapper.mapToDTO(userService.getUserByEmail(email), UserDTO.class);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a list of all users.
     * @return A ResponseEntity containing a list of user DTOs if found, or a "not found" response.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers().stream()
                .map(user -> mapper.mapToDTO(user, UserDTO.class))
                .toList();

        if(userDTOs.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDTOs);
    }

    /**
     * Create a new user.
     * @param userDTO The user DTO to create.
     * @return A ResponseEntity containing the created user's DTO if successful, or a "bad request" response if there is an issue with the request.
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        try {
            User createdUser = userService.createUser(mapper.mapToEntity(userDTO, User.class));
            UserDTO createdUserDTO = mapper.mapToDTO(createdUser, UserDTO.class);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a user.
     * @param userId The ID of the user to update.
     * @param userDTO The user DTO to update.
     * @return A ResponseEntity containing the updated user's DTO if successful, or a "not found" response if the user was not found.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            User updatedUser = userService.updateUser(userId, mapper.mapToEntity(userDTO, User.class));
            UserDTO updatedUserDTO = mapper.mapToDTO(updatedUser, UserDTO.class);

            return ResponseEntity.ok(updatedUserDTO);

        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a user.
     * @param userId The ID of the user to delete.
     * @return A ResponseEntity containing no content if successful, or a "not found" response if the user was not found.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Set the UserService.
     * @param userService The UserService to set.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Set the InternalModelMapper.
     * @param mapper The InternalModelMapper to set.
     */
    @Autowired
    public void setMapper(InternalModelMapper mapper) {
        this.mapper = mapper;
    }
}
