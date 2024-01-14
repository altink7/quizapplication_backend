package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Gender;
import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.UserStatistic;
import at.technikum.springrestbackend.validator.UsernameOrEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Max(Long.MAX_VALUE)
    @Min(Long.MIN_VALUE)
    private Long id;

    private Gender salutation;

    private String otherSalutationDetail;

    @NotNull(message = "First name cannot be null!")
    @Size(min = 2, max = 20, message = "First Name must be between 4 and 20 characters long!")
    private String firstName;

    @NotNull(message = "Last name cannot be null!")
    @Size(min = 2, max = 20, message = "First Name must be between 4 and 20 characters long!")
    private String lastName;

    @NotNull(message = "Email cannot be null!")
    @UsernameOrEmail(message = "Not a valid email or username!")
    private String emailOrUsername;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "Password needs to be at least 8 characters long!")
    private char[] password;

    private String country;

    @NotNull(message = "Role cannot be null!")
    private Role role;

    private UserStatistic userStatistic;
}
