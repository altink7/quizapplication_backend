package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Gender;
import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.UserStatistic;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    @NotNull(message = "Gender cannot be null!")
    private Gender salutation;

    @NotNull(message = "First name cannot be null!")
    @NotBlank(message = "First name cannot be blank!")
    private String firstName;

    @NotNull(message = "Last name cannot be null!")
    @NotBlank(message = "Last name cannot be blank!")
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
