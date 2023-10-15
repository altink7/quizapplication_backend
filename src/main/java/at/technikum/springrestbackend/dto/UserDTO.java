package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Gender;
import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.UserStatistic;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {
    private Gender salutation;
    private String firstName;
    private String lastName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String country;
    private Role role;
    private UserStatistic userStatistic;
}
