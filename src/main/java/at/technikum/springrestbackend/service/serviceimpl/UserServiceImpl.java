package at.technikum.springrestbackend.service.serviceimpl;

import at.technikum.springrestbackend.config.mapper.InternalModelMapper;
import at.technikum.springrestbackend.dto.CredentialsDTO;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.exceptions.InvalidPasswordException;
import at.technikum.springrestbackend.exceptions.UserAlreadyExistsException;
import at.technikum.springrestbackend.exceptions.UserNotFoundException;
import at.technikum.springrestbackend.model.Gender;
import at.technikum.springrestbackend.model.ProfilPicture;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserDao;
import at.technikum.springrestbackend.service.UserService;
import at.technikum.springrestbackend.storage.FileStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.CharBuffer;
import java.util.*;


/**
 * Implementation of the UserService interface
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final FileStorage fileStorage;
    private final ObjectMapper objectMapper;
    private final InternalModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, FileStorage fileStorage, ObjectMapper objectMapper, InternalModelMapper mapper) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.fileStorage = fileStorage;
        this.objectMapper = objectMapper;
        this.mapper = mapper;
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

            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
            }

            return userDao.save(existingUser);
        }).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUpdatedUser(Long userId, Map<String, Object> requestMap) {
        UserDTO userDTO = objectMapper.convertValue(requestMap.get("user"), UserDTO.class);
        User updatedUser = updateUser(userId, mapper.mapToEntity(userDTO, User.class));

        if (requestMap.containsKey("file") && requestMap.get("file") != null) {
            String base64File = (String) requestMap.get("file");
            MultipartFile file = convertBase64ToFile(base64File, userDTO.getFirstName());
            uploadProfilePicture(file, userId);
        }
        return updatedUser;
    }

    @Override
    public Map<Resource, MediaType> getProfilePicture(Long userId) {
        User user = userDao.findById(userId).orElseThrow(UserNotFoundException::new);
        if(user.getProfilPicture() != null && user.getProfilPicture().getExternalId() != null) {
            InputStream stream = fileStorage.load(user.getProfilPicture().getExternalId());
            MediaType mediaType = MediaType.parseMediaType(user.getProfilPicture().getContentType());

            return Map.of(new InputStreamResource(stream), mediaType);
        }
        return Collections.emptyMap();
    }

    protected void uploadProfilePicture(MultipartFile file, Long userId) {
        String keyId = fileStorage.upload(file);
        ProfilPicture profilPicture = new ProfilPicture();
        profilPicture.setName(file.getOriginalFilename());
        profilPicture.setExternalId(keyId);
        profilPicture.setContentType(file.getContentType());

        userDao.findById(userId).map(user -> {
            user.setProfilPicture(profilPicture);
            return userDao.save(user);
        }).orElseThrow(UserNotFoundException::new);
    }

    private MultipartFile convertBase64ToFile(String base64File, String originalFilename) {
        MultipartFile multipartFile;
        Base64.Decoder decoder = Base64.getDecoder();
        String[] parts = base64File.split(",");
        byte[] decodedBytes = decoder.decode(parts[1]);
        String name = UUID.randomUUID().toString();
        String contentType = parts[0].split(";")[0].split(":")[1];

        multipartFile = new MockMultipartFile(
                name,
                originalFilename,
                contentType,
                decodedBytes
        );

        return multipartFile;
    }
}
