package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Gender;
import lombok.Data;

@Data
public class UserDTO {
    private Gender salutation;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String country;
}
