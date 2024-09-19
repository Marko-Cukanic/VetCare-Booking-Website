package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.PrescriptionController;
import au.edu.rmit.sept.webapp.service.PrescriptionService;
import au.edu.rmit.sept.webapp.service.PrescriptionHistoryService;
import au.edu.rmit.sept.webapp.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebMvcTest(PrescriptionController.class) // Focus only on PrescriptionController
public class PrescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrescriptionService prescriptionService;

    @MockBean
    private PrescriptionHistoryService prescriptionHistoryService;

    @MockBean
    private LoginController loginController;

    @Test
    public void getPrescriptionsAndHistory_ValidSessionToken_Success() throws Exception {
        // Mock session token and login response
        String sessionToken = "mock-session-token";
        String email = "testuser@example.com";
        Map<String, String> sessionTokens = new HashMap<>();
        sessionTokens.put(sessionToken, email);

        when(loginController.getSessionTokens()).thenReturn(sessionTokens);
        when(prescriptionService.getPrescriptionsByEmail(email)).thenReturn(Collections.emptyList());
        when(prescriptionHistoryService.getPrescriptionHistoriesByEmail(email)).thenReturn(Collections.emptyList());

        // Perform a GET request with a valid session token
        mockMvc.perform(get("/prescriptions").param("sessionToken", sessionToken))
                .andExpect(status().isOk())
                .andExpect(view().name("prescriptions"))
                .andExpect(model().attribute("isLoggedIn", true))
                .andExpect(model().attribute("prescriptions", Collections.emptyList()))
                .andExpect(model().attribute("histories", Collections.emptyList()));
    }

    @Test
    public void getPrescriptionsAndHistory_NoSessionToken_Failure() throws Exception {
        // Perform a GET request without a session token
        mockMvc.perform(get("/prescriptions"))
                .andExpect(status().isOk())
                .andExpect(view().name("prescriptions"))
                .andExpect(model().attribute("isLoggedIn", false));
    }
}
