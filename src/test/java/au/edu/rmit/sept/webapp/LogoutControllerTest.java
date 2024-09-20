package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.LoginController;
import au.edu.rmit.sept.webapp.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(LoginController.class) // Focus only on the LoginController
public class LogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;  

    @Test
    public void logoutUser_ValidSession_Success() throws Exception {
        // Mock a session token
        String sessionToken = "mock-session-token";

        // Perform a GET request to logout
        mockMvc.perform(get("/logout")
                .param("sessionToken", sessionToken))
                .andExpect(status().isOk())
                .andExpect(content().string("You have successfully logged out."));
    }

    @Test
    public void logoutUser_NoSessionToken_Success() throws Exception {
        // Perform a GET request without a session token
        mockMvc.perform(get("/logout"))
                .andExpect(status().isOk())
                .andExpect(content().string("You have successfully logged out."));
    }
}
