package au.edu.rmit.sept.webapp.controllerTest;

import au.edu.rmit.sept.webapp.controller.PrescriptionController;  // Add this import
import au.edu.rmit.sept.webapp.model.Prescription;
import au.edu.rmit.sept.webapp.model.PrescriptionHistory;
import au.edu.rmit.sept.webapp.service.PrescriptionService;
import au.edu.rmit.sept.webapp.service.PrescriptionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PrescriptionControllerTest {

    @Mock
    private PrescriptionService prescriptionService;

    @Mock
    private PrescriptionHistoryService prescriptionHistoryService;

    @Mock
    private Model model;

    @InjectMocks
    private PrescriptionController prescriptionController;  // Ensure this is properly recognized

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPrescriptionsAndHistory_shouldAddDataToModel() {
        // Mock prescriptions and histories
        List<Prescription> mockPrescriptions = Arrays.asList(new Prescription(), new Prescription());
        List<PrescriptionHistory> mockHistories = Arrays.asList(new PrescriptionHistory(), new PrescriptionHistory());

        // Mock the services to return data
        when(prescriptionService.getAllPrescriptions()).thenReturn(mockPrescriptions);
        when(prescriptionHistoryService.getAllPrescriptionHistories()).thenReturn(mockHistories);

        // Call the method to be tested
        String result = prescriptionController.getPrescriptionsAndHistory(model);

        // Verify the model interactions
        verify(model).addAttribute("prescriptions", mockPrescriptions);
        verify(model).addAttribute("histories", mockHistories);

        // Verify the template returned
        assertEquals("prescriptions", result);
    }

    @Test
    void getPrescriptionsAndHistory_shouldHandleEmptyData() {
        // Mock empty lists
        List<Prescription> mockPrescriptions = Arrays.asList();
        List<PrescriptionHistory> mockHistories = Arrays.asList();

        // Mock the services to return empty data
        when(prescriptionService.getAllPrescriptions()).thenReturn(mockPrescriptions);
        when(prescriptionHistoryService.getAllPrescriptionHistories()).thenReturn(mockHistories);

        // Call the method to be tested
        String result = prescriptionController.getPrescriptionsAndHistory(model);

        // Verify the model interactions
        verify(model).addAttribute("prescriptions", mockPrescriptions);
        verify(model).addAttribute("histories", mockHistories);

        // Verify the template returned
        assertEquals("prescriptions", result);
    }
}
