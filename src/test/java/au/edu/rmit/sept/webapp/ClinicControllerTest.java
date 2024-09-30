package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.ClinicController;
import au.edu.rmit.sept.webapp.model.Clinic;
import au.edu.rmit.sept.webapp.service.ClinicService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(ClinicController.class)
public class ClinicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClinicService clinicService;

    @Test
    public void showClinicSelector_DisplayAllClinics_Success() throws Exception {
        // Mock a list of clinics
        Clinic clinic1 = new Clinic();
        Clinic clinic2 = new Clinic();
        Mockito.when(clinicService.getAllClinics()).thenReturn(Arrays.asList(clinic1, clinic2));

        // Perform GET request to fetch clinics
        mockMvc.perform(get("/clinicSelector"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("clinics"));
    }
}
