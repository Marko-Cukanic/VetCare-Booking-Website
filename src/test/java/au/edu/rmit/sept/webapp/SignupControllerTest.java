package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.SignupController;
import au.edu.rmit.sept.webapp.repository.UserRepository;
import au.edu.rmit.sept.webapp.service.EmailService;
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

@WebMvcTest(SignupController.class)
public class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailService emailService;

    @Test
    public void signupUser_ValidUser_Success() throws Exception {
        // Positive boundary test: Valid user signup
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        mockMvc.perform(post("/signup")
                .param("fullname", "Thomas Saleh")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password123"))
                .andExpect(status().is3xxRedirection());  // Expect a redirect on successful signup
    }

    @Test
    public void signupUser_EmailAlreadyExists_Error() throws Exception {
        // Negative boundary test: User with the same email already exists
        User existingUser = new User(1L, "Thomas Saleh", "test@example.com", "password123");
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(existingUser);

        mockMvc.perform(post("/signup")
                .param("fullname", "Thomas Saleh")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("This email is already in use")));
    }

    @Test
    public void signupUser_PasswordsDoNotMatch_Error() throws Exception {
        // Negative boundary test: Passwords do not match
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        mockMvc.perform(post("/signup")
                .param("fullname", "Thomas Saleh")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password456"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Passwords do not match")));
    }

    @Test
    public void signupUser_EmptyFullname_Error() throws Exception {
        // Negative boundary test: Empty fullname
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        mockMvc.perform(post("/signup")
                .param("fullname", "")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Fullname is required")));
    }

    @Test
    public void signupUser_PasswordTooShort_Error() throws Exception {
        // Negative boundary test: Password below minimum length
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        mockMvc.perform(post("/signup")
                .param("fullname", "Thomas Saleh")
                .param("email", "test@example.com")
                .param("password", "short")
                .param("confirmPassword", "short"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Password must be at least 8 characters")));
    }

    @Test
    public void signupUser_PasswordMinLength_Success() throws Exception {
        // Positive boundary test: Password at minimum length
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        mockMvc.perform(post("/signup")
                .param("fullname", "Thomas Saleh")
                .param("email", "test@example.com")
                .param("password", "password")
                .param("confirmPassword", "password"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void signupUser_PasswordTooLong_Error() throws Exception {
        // Negative boundary test: Password exceeds maximum length
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        String longPassword = "a".repeat(101); // Assuming 100 is the maximum length
        mockMvc.perform(post("/signup")
                .param("fullname", "Thomas Saleh")
                .param("email", "test@example.com")
                .param("password", longPassword)
                .param("confirmPassword", longPassword))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Password must be at most 100 characters")));
    }

    @Test
    public void signupUser_PasswordMaxLength_Success() throws Exception {
        // Positive boundary test: Password at maximum length
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        String maxLengthPassword = "a".repeat(100); // Assuming 100 is the maximum length
        mockMvc.perform(post("/signup")
                .param("fullname", "Thomas Saleh")
                .param("email", "test@example.com")
                .param("password", maxLengthPassword)
                .param("confirmPassword", maxLengthPassword))
                .andExpect(status().is3xxRedirection());
    }
}
