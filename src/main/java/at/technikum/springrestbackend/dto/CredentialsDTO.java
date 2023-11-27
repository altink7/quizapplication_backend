package at.technikum.springrestbackend.dto;

import lombok.Data;

@Data
public class CredentialsDTO {

    private String email;
    private char[] password;
}
