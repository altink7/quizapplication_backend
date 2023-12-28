package at.technikum.springrestbackend.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CorsConfigTest {
    @Mock
    private CorsRegistry corsRegistry;

    @Mock
    private CorsRegistration corsRegistration;

    private CorsConfig corsConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        corsConfig = new CorsConfig();

        ReflectionTestUtils.setField(corsConfig, "frontendWebUrl", "http://example.com");
        ReflectionTestUtils.setField(corsConfig, "allowedMethods", "GET,POST,PUT,DELETE");
        ReflectionTestUtils.setField(corsConfig, "maxAge", 3600);
    }

    @Test
    public void testCorsConfigurer() {
        when(corsRegistry.addMapping("/api/**")).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins("http://example.com")).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods("GET", "POST", "PUT", "DELETE")).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders("*")).thenReturn(corsRegistration);

        WebMvcConfigurer configurer = corsConfig.corsConfigurer();
        configurer.addCorsMappings(corsRegistry);

        // verify that the corsRegistry is called with the correct parameters
        verify(corsRegistration).allowedOrigins("http://example.com");
        verify(corsRegistration).allowedMethods("GET", "POST", "PUT", "DELETE");
        verify(corsRegistration).maxAge(3600);
    }
}
