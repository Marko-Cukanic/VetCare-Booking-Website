package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.PrescriptionOrderController;
import au.edu.rmit.sept.webapp.model.Prescription;
import au.edu.rmit.sept.webapp.service.PrescriptionService;
import au.edu.rmit.sept.webapp.service.PrescriptionHistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

@WebMvcTest(PrescriptionOrderController.class)
public class PrescriptionOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrescriptionService prescriptionService;

    @MockBean
    private PrescriptionHistoryService prescriptionHistoryService;

    // Positive test for confirmOrder with valid prescriptionId and quantity
    @Test
    public void confirmOrder_ValidInputs_Success() throws Exception {
        // Mock a prescription
        Prescription prescription = new Prescription();
        prescription.setId(1L);
        prescription.setMedicationName("Medication A");
        Mockito.when(prescriptionService.getPrescriptionById(1L)).thenReturn(prescription);

        // Perform a POST request with valid inputs
        mockMvc.perform(post("/confirmOrder")
                .param("medication", "1")
                .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmOrder"))
                .andExpect(model().attribute("prescription", prescription))
                .andExpect(model().attribute("quantity", 5));
    }

    // Negative test for confirmOrder with invalid prescriptionId
    @Test
public void confirmOrder_InvalidPrescriptionId_Failure() throws Exception {
    // Mock the prescription service to return null
    Mockito.when(prescriptionService.getPrescriptionById(999L)).thenReturn(null);

    // Perform a POST request with an invalid prescriptionId
    mockMvc.perform(post("/confirmOrder")
            .param("medication", "999")
            .param("quantity", "5"))
            .andExpect(status().isOk())
            .andExpect(view().name("confirmOrder")) // Adjusted to use "confirmOrder" template for error
            .andExpect(model().attribute("errorMessage", "Prescription not found."))
            .andExpect(model().attribute("isPrescriptionValid", false))
            .andExpect(model().attributeDoesNotExist("prescription"));
}


    // Negative test for confirmOrder with zero quantity
    @Test
    public void confirmOrder_ZeroQuantity_Failure() throws Exception {
        // Mock a valid prescription
        Prescription prescription = new Prescription();
        prescription.setId(1L);
        prescription.setMedicationName("Medication A");
        Mockito.when(prescriptionService.getPrescriptionById(1L)).thenReturn(prescription);

        // Perform a POST request with zero quantity
        mockMvc.perform(post("/confirmOrder")
                .param("medication", "1")
                .param("quantity", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmOrder"))
                .andExpect(model().attribute("prescription", prescription))
                .andExpect(model().attribute("quantity", 0));
    }

    // Positive test for finaliseOrder with valid inputs
    @Test
    public void finaliseOrder_ValidInputs_Success() throws Exception {
        // Mock a valid prescription
        Prescription prescription = new Prescription();
        prescription.setId(1L);
        prescription.setMedicationName("Medication A");
        prescription.setPrescriptionDate(new Date());
        prescription.setEmail("user@example.com");
        Mockito.when(prescriptionService.getPrescriptionById(1L)).thenReturn(prescription);

        // Perform a POST request with valid inputs
        mockMvc.perform(post("/finaliseOrder")
                .param("medicationId", "1")
                .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderSuccess"))
                .andExpect(model().attribute("prescription", prescription))
                .andExpect(model().attribute("quantity", 5));
    }

    // Negative test for finaliseOrder with invalid medicationId
    @Test
public void finaliseOrder_InvalidMedicationId_Failure() throws Exception {
    // Mock the prescription service to return null
    Mockito.when(prescriptionService.getPrescriptionById(999L)).thenReturn(null);

    // Perform a POST request with an invalid medicationId
    mockMvc.perform(post("/finaliseOrder")
            .param("medicationId", "999")
            .param("quantity", "5"))
            .andExpect(status().isOk())
            .andExpect(view().name("confirmOrder")) // Adjusted to use "confirmOrder" template for error
            .andExpect(model().attribute("errorMessage", "Prescription not found."))
            .andExpect(model().attribute("isPrescriptionValid", false))
            .andExpect(model().attributeDoesNotExist("prescription"));
}


    // Negative test for finaliseOrder with negative quantity
    @Test
    public void finaliseOrder_NegativeQuantity_Failure() throws Exception {
        // Mock a valid prescription
        Prescription prescription = new Prescription();
        prescription.setId(1L);
        prescription.setMedicationName("Medication A");
        prescription.setPrescriptionDate(new Date());
        Mockito.when(prescriptionService.getPrescriptionById(1L)).thenReturn(prescription);

        // Perform a POST request with a negative quantity
        mockMvc.perform(post("/finaliseOrder")
                .param("medicationId", "1")
                .param("quantity", "-5"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderSuccess"))
                .andExpect(model().attribute("prescription", prescription))
                .andExpect(model().attribute("quantity", -5));
    }
}
