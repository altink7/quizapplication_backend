package at.technikum.springrestbackend.config.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PasswordConfigTest {

    @Test
    void testPasswordEncoderBean() {
        PasswordConfig passwordConfig = new PasswordConfig();
        PasswordEncoder passwordEncoder = passwordConfig.passwordEncoder();

        assertNotNull(passwordEncoder);
        assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoder);
    }
}
