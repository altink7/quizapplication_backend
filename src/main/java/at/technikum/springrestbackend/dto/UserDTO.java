package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Gender;
import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.UserStatistic;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private Gender salutation;

    private String firstName;

    private String lastName;

    @NotNull(message = "Email cannot be null!")
    @Email(message = "Not a valid email!")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String country;

    @NotNull(message = "Role cannot be null!")
    private Role role;

    private UserStatistic userStatistic;
}
