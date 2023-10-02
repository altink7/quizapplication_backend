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

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean deleteUser(Long userId) {
        return userDao.findById(userId).map(user -> {
            userDao.delete(user);
            return true;
        }).orElse(false);
    }

    @ValidateWith(UserValidator.class)
    public User createUser(User user) {
        return userDao.save(user);
    }

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

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
