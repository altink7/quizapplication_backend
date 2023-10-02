package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Gender;
import at.technikum.springrestbackend.model.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private Gender salutation;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String country;
    private Role role;
}
