package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.CredentialsDTO;
import at.technikum.springrestbackend.model.User;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    /**
     * Get all users
     *
     * @return the list of users
     */
    List<User> getAllUsers();

    /**
     * Get a user by id
     *
     * @param userId the user id
     * @return the user
     */
    User getUserById(Long userId);

    /**
     * Get a user by email
     *
     * @param email the email
     * @return the user
     */
    User getUserByEmail(String email);

    /**
     * Delete a user by id
     *
     * @param userId the user id
     * @return true if the user was deleted, false otherwise
     */
    boolean deleteUser(Long userId);

    /**
     * Create a new user (register)
     *
     * @param user the user
     * @return the created user
     */
    User register(User user);

    /**
     * Login
     *
     * @param credentialsDTO the DTO with all credentials
     * @return the logged-in User
     */
    User login(CredentialsDTO credentialsDTO);

    /**
     * Update a user
     *
     * @param userId the user id
     * @param user   the user
     * @return the updated user
     */
    User updateUser(Long userId, User user);

    User getUpdatedUser(Long userId, Map<String, Object> requestMap);

    Map<Resource, MediaType> getProfilePicture(Long userId);
}
