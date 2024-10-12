package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.MedicalController;
import au.edu.rmit.sept.webapp.controller.LoginController;

import au.edu.rmit.sept.webapp.model.Medical;
import au.edu.rmit.sept.webapp.model.Vaccination;
import au.edu.rmit.sept.webapp.model.MedicalCondition;
import au.edu.rmit.sept.webapp.model.TreatmentPlan;
import au.edu.rmit.sept.webapp.service.EmailService;

import au.edu.rmit.sept.webapp.service.MedicalConditionService;
import au.edu.rmit.sept.webapp.service.MedicalService;
import au.edu.rmit.sept.webapp.service.TreatmentPlanService;
import au.edu.rmit.sept.webapp.service.VaccinationService;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MedicalControllerTest {

    private String email = "user@example.com"; 
    private String petName = "Fluffy"; 
    private String sessionToken = "validToken"; 

    @InjectMocks
    private MedicalController medicalController;

    @Mock
    private MedicalService medicalService;

    @Mock
    private VaccinationService vaccinationService;

    @Mock
    private MedicalConditionService medicalConditionService;

    @Mock
    private TreatmentPlanService treatmentPlanService;

    @Mock
    private EmailService emailService;

    @Mock
    private LoginController loginController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------------------------------------ Show Medical History Tests ------------------------------------------------------
    @Test
    void showMedicalHistory_notLoggedIn() {
        // Arrange
        when(loginController.getSessionTokens()).thenReturn(Map.of());

        // Act
        String viewName = medicalController.showMedicalHistory(sessionToken, null, model);

        // Assert
        assertEquals("medical", viewName);
        verify(model).addAttribute("isLoggedIn", false);
    }

    // ------------------------------------------------------ Add Report Tests ------------------------------------------------------
    @Test
    void addReport_success() throws MedicalService.DuplicateRecordException {
        // Arrange
        Medical medical = new Medical();
        Vaccination vaccination = new Vaccination();
        List<String> medicalConditions = List.of("Condition 1");
        List<String> treatmentNames = List.of("Treatment 1");
        List<String> treatmentDates = List.of("2024-10-08");

        doNothing().when(medicalService).saveMedicalRecord(any(Medical.class));
        doNothing().when(vaccinationService).saveVaccinationRecord(any(Vaccination.class));
        doNothing().when(medicalConditionService).saveMedicalCondition(any(MedicalCondition.class));
        doNothing().when(treatmentPlanService).saveTreatmentPlan(any(TreatmentPlan.class));

        // Act
        String viewName = medicalController.addOrUpdateReport(medical, vaccination, email, petName, medicalConditions, treatmentNames, treatmentDates, model);

        // Assert
        assertEquals("redirect:/medical", viewName);
    }

    @Test
    void addReport_duplicateRecord() throws MedicalService.DuplicateRecordException {
        // Arrange
        Medical medical = new Medical();
        doThrow(new MedicalService.DuplicateRecordException("A record with this petID already exists."))
                .when(medicalService).saveMedicalRecord(any(Medical.class));

        // Act
        String viewName = medicalController.addOrUpdateReport(medical, new Vaccination(), email, petName, List.of("Condition 1"), List.of("Treatment 1"), List.of("2024-10-08"), model);

        // Assert
        assertEquals("addReport", viewName);
        verify(model).addAttribute("error", "A record with this petID already exists.");
    }

    // ------------------------------------------------------ Share Report Tests ------------------------------------------------------
    @Test
    void shareReport_success() throws IOException, MessagingException {
        // Arrange
        MultipartFile pdfFile = mock(MultipartFile.class);
        byte[] pdfBytes = "sample pdf content".getBytes();
        when(pdfFile.getBytes()).thenReturn(pdfBytes);

        // Act
        String viewName = medicalController.shareReport(email, pdfFile, model);

        // Assert
        assertEquals("redirect:/medical", viewName);
        verify(emailService).sendEmailWithAttachment(email, pdfBytes);
        verify(model).addAttribute("successMessage", "Medical report shared successfully!");
    }

    @Test
    void shareReport_error() throws IOException, MessagingException {
        // Arrange
        MultipartFile pdfFile = mock(MultipartFile.class);
        when(pdfFile.getBytes()).thenThrow(new IOException("File not found"));

        // Act
        String viewName = medicalController.shareReport(email, pdfFile, model);

        // Assert
        assertEquals("redirect:/medical", viewName);
        verify(model).addAttribute("errorMessage", "Error while sharing the medical report: File not found");
    }
}
