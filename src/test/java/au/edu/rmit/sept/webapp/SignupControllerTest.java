package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.SignupController;
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
import au.edu.rmit.sept.webapp.model.User;

@WebMvcTest(SignupController.class) // Focus only on the SignupController
public class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;  // Mock the repository to isolate the test

    @Test
    public void signupUser_ValidUser_Success() throws Exception {
        // Mock the behavior of the UserRepository to return no user (meaning the email is not registered)
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        // Perform a POST request to the signup endpoint
        mockMvc.perform(post("/signup")
                .param("fullname", "John Doe")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password123"))
                .andExpect(status().is3xxRedirection());  // Expect a redirection on success
    }

    @Test
    public void signupUser_EmailAlreadyExists_Error() throws Exception {
        // Mock an existing user with the same email
        User existingUser = new User(1L, "John Doe", "test@example.com", "password123");
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(existingUser);

        // Perform a POST request to the signup endpoint
        mockMvc.perform(post("/signup")
                .param("fullname", "John Doe")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password123"))
                .andExpect(status().isOk())  // Should return OK and show the signup page again
                .andExpect(content().string(org.hamcrest.Matchers.containsString("This email is already in use")));
    }
}
