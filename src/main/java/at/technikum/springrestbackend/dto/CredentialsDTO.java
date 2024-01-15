package at.technikum.springrestbackend.dto;

import lombok.Data;

@Data
public class CredentialsDTO {

    private String emailOrUsername;
    private char[] password;
}
