package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.LoginController;
import au.edu.rmit.sept.webapp.model.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(LoginController.class) // Focus only on the LoginController
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;  // Mock the repository to isolate the test

    @Autowired
    private LoginController loginController; // Used to directly manipulate the controller state

    @Test
    public void loginUser_ValidCredentials_Success() throws Exception {
        // Mock an existing user with valid credentials
        User validUser = new User(1L, "John Doe", "test@example.com", "password123");
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(validUser);

        // Perform a POST request to the login endpoint
        mockMvc.perform(post("/login")
                .param("email", "test@example.com")
                .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.emptyOrNullString())));  // Expect a session token
    }

    @Test
    public void loginUser_InvalidCredentials_Error() throws Exception {
        // Mock the repository to return no user, simulating invalid credentials
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        // Perform a POST request to the login endpoint
        mockMvc.perform(post("/login")
                .param("email", "test@example.com")
                .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid email or password."));
    }

    @Test
public void loginUser_AlreadyLoggedIn_Message() throws Exception {
    // Simulate a user already logged in by setting session attributes
    String sessionToken = "mock-session-token";
    loginController.getSessionTokens().put(sessionToken, "test@example.com");

    // Mock HTTP session attributes
    mockMvc.perform(post("/login")
            .sessionAttr("loggedIn", true)
            .sessionAttr("userEmail", "test@example.com")
            .param("email", "newuser@example.com")
            .param("password", "password123"))
            .andExpect(status().isOk())
            .andExpect(content().string("You are already logged in!"));
}

}
