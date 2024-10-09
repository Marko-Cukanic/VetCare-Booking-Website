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

    @Autowired
    private LoginController loginController; // Used to directly manipulate the controller state

    @Test
    public void logoutUser_ValidSession_Success() throws Exception {
        // Simulate a user being logged in by adding a session token to the sessionTokens map
        String sessionToken = "mock-session-token";
        loginController.getSessionTokens().put(sessionToken, "test@example.com");

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

    @Test
    public void logoutUser_InvalidSessionToken_Success() throws Exception {
        // Perform a GET request with an invalid session token
        mockMvc.perform(get("/logout")
                .param("sessionToken", "invalid-session-token"))
                .andExpect(status().isOk())
                .andExpect(content().string("You have successfully logged out."));
    }
}
