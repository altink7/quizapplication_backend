package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.dto.CredentialsDTO;
import at.technikum.springrestbackend.exceptions.InvalidPasswordException;
import at.technikum.springrestbackend.exceptions.UserAlreadyExistsException;
import at.technikum.springrestbackend.exceptions.UserNotFoundException;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserDao;
import at.technikum.springrestbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserService interface
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userDao.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public boolean deleteUser(Long userId) {
        return userDao.findById(userId).map(user -> {
            userDao.delete(user);
            return true;
        }).orElse(false);
    }

    public User register(User user) {
        Optional<User> oUser = userDao.findByEmail(user.getEmail());

        if (oUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(user.getPassword())));
        return userDao.save(user);
    }

    @Override
    public User login(CredentialsDTO credentialsDTO) {
        User user = userDao.findByEmail(credentialsDTO.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.getPassword()),
                user.getPassword())) {
            return user;
        }
        throw new InvalidPasswordException();
    }

    @Override
    public User updateUser(Long userId, User user) {
        return userDao.findById(userId).map(u -> {
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            return userDao.save(u);
        }).orElseThrow(UserNotFoundException::new);
    }
}
