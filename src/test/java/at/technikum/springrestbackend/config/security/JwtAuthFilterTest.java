package at.technikum.springrestbackend.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class JwtAuthFilterTest {

    private MockMvc mockMvc;

    @Mock
    private UserAuthProvider userAuthProvider;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup()
                .addFilter(jwtAuthFilter)
                .build();
    }

    @Test
    void testDoFilterInternalWithInvalidToken() throws Exception {
        String invalidToken = "invalidToken";
        when(userAuthProvider.validateTokenStrongly(invalidToken)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/someEndpoint")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void testDoFilterInternalNoToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        verifyNoInteractions(userAuthProvider);
    }
}