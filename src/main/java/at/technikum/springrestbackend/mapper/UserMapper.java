package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setSalutation(user.getSalutation());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setCountry(user.getCountry());

        return userDTO;
    }

    public User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setSalutation(userDTO.getSalutation());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setCountry(userDTO.getCountry());

        return user;
    }
}
