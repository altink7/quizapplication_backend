package at.technikum.springrestbackend.serviceimpl;

import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserDao;
import at.technikum.springrestbackend.service.UserService;
import at.technikum.springrestbackend.validator.annotation.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.technikum.springrestbackend.validator.modelvalidator.UserValidator;

import java.util.List;

/**
 * Implementation of the UserService interface
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    /**
     * Get all users
     * @return the list of users
     */
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    /**
     * Get a user by id
     * @param userId the user id
     * @return the user
     */
    @Override
    public User getUserById(Long userId) {
        return userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Get a user by email
     * @param email the email
     * @return the user
     */
    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Delete a user by id
     * @param userId the user id
     * @return true if the user was deleted, false otherwise
     */
    @Override
    public boolean deleteUser(Long userId) {
        return userDao.findById(userId).map(user -> {
            userDao.delete(user);
            return true;
        }).orElse(false);
    }

    /**
     * Create a new user
     * @param user the user
     * @return the created user
     */
    @ValidateWith(UserValidator.class)
    public User createUser(User user) {
        return userDao.save(user);
    }

    /**
     * Update a user
     * @param userId the user id
     * @param user the user
     * @return the updated user
     */
    @Override
    public User updateUser(Long userId, User user) {
        return userDao.findById(userId).map(u -> {
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            return userDao.save(u);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Set the userDao
     * @param userDao the user dao
     */
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
