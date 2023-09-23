package at.technikum.springrestbackend.service;

import java.util.List;
import at.technikum.springrestbackend.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    /**
     * Get all users
     * @return the list of users
     */
    List<User> getAllUsers();

    /**
     * Get a user by id
     * @param userId the user id
     * @return the user
     */
    User getUserById(Long userId);

    /**
     * Get a user by email
     * @param email the email
     * @return the user
     */
    User getUserByEmail(String email);

    /**
     * Delete a user by id
     * @param userId the user id
     * @return true if the user was deleted, false otherwise
     */
    boolean deleteUser(Long userId);

    /**
     * Create a new user
     * @param user the user
     * @return the created user
     */
    User createUser(User user);

    /**
     * Update a user
     * @param userId the user id
     * @param user the user
     * @return the updated user
     */
    User updateUser(Long userId, User user);

}
