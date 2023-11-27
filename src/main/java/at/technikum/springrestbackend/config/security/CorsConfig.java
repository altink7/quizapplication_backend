package at.technikum.springrestbackend.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${frontend.web.url}")
    private String frontendWebUrl;

    @Value("${cors.allowed.methods}")
    private String allowedMethods;

    @Value("${cors.max.age}")
    private int maxAge;

    /**
     * Configures the CORS settings for the application
     *
     * @return The WebMvcConfigurer instance
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(frontendWebUrl)
                        .allowedMethods(allowedMethods.split(","))
                        .maxAge(maxAge);
            }
        };
    }
}