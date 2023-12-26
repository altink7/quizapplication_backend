package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.dto.CredentialsDTO;
import at.technikum.springrestbackend.exceptions.InvalidPasswordException;
import at.technikum.springrestbackend.exceptions.UserAlreadyExistsException;
import at.technikum.springrestbackend.exceptions.UserNotFoundException;
import at.technikum.springrestbackend.model.ProfilPicture;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserDao;
import at.technikum.springrestbackend.service.UserService;
import at.technikum.springrestbackend.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import at.technikum.springrestbackend.model.Gender;


/**
 * Implementation of the UserService interface
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final FileStorage fileStorage;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, FileStorage fileStorage) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.fileStorage = fileStorage;
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

        user.setPassword(getEncode(user.getPassword()));
        return userDao.save(user);
    }

    private String getEncode(String password ) {
        return passwordEncoder.encode(CharBuffer.wrap(password));
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
        return userDao.findById(userId).map(existingUser -> {
            if (user.getFirstName() != null) {
                existingUser.setFirstName(user.getFirstName());
            }

            if (user.getLastName() != null) {
                existingUser.setLastName(user.getLastName());
            }

            if (user.getSalutation() != null) {
                existingUser.setSalutation(user.getSalutation());
                if (user.getSalutation().equals(Gender.OTHER) && user.getOtherSalutationDetail() != null) {
                    existingUser.setOtherSalutationDetail(user.getOtherSalutationDetail());
                }
            }

            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(getEncode(user.getPassword()));
            }

            if (user.getCountry() != null) {
                existingUser.setCountry(user.getCountry());
            }

            return userDao.save(existingUser);
        }).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User uploadProfilePicture(MultipartFile file, Long userId) {
        String keyId = fileStorage.upload(file);
        ProfilPicture profilPicture = new ProfilPicture();
        profilPicture.setName(file.getOriginalFilename());
        profilPicture.setExternalId(keyId);
        profilPicture.setContentType(file.getContentType());

        return userDao.findById(userId).map(user -> {
            user.setProfilPicture(profilPicture);
            return userDao.save(user);
        }).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Map<Resource, MediaType> getProfilePicture(Long userId) {
        User user = userDao.findById(userId).orElseThrow(UserNotFoundException::new);
        InputStream stream = fileStorage.load(user.getProfilPicture().getExternalId());
        MediaType mediaType = MediaType.parseMediaType(user.getProfilPicture().getContentType());

        return Map.of(new InputStreamResource(stream), mediaType);
    }
}
