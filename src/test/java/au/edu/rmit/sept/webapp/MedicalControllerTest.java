package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.LoginController;
import au.edu.rmit.sept.webapp.controller.MedicalController;
import au.edu.rmit.sept.webapp.model.*;
import au.edu.rmit.sept.webapp.service.*;
import au.edu.rmit.sept.webapp.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MedicalController.class)
public class MedicalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginController loginController;

    @MockBean
    private UserRepository userRepository;  

    @MockBean
    private MedicalService medicalService;

    @MockBean
    private VaccinationService vaccinationService;

    @MockBean
    private MedicalConditionService medicalConditionService;

    @MockBean
    private TreatmentPlanService treatmentPlanService;

    @MockBean
    private MedicalRepository medicalRepository;

    @MockBean
    private MedicalConditionRepository medicalConditionRepository;

    @MockBean
    private VaccinationRepository vaccinationRepository;

    @MockBean
    private TreatmentPlanRepository treatmentPlanRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private static final String TEST_EMAIL = "user@example.com";
    private static final String TEST_PET_NAME = "Buddy";

    //Mock Medical Details
    private Medical createMockMedical() {
        // Setup medical report data
        Medical medicalRecord = new Medical();
        medicalRecord.setEmail(TEST_EMAIL);
        medicalRecord.setPetName(TEST_PET_NAME);
        medicalRecord.setPetID(1L);
        medicalRecord.setPetAge(5);
        medicalRecord.setPetSex("Male");
        medicalRecord.setPetWeight(25);
        medicalRecord.setPetType("Dog");
        medicalRecord.setPetBreed("Labrador");
        return medicalRecord;
    }

    //Mock Vaccination Details
    private Vaccination createMockVaccination() {
        // Setup vaccination data
        Vaccination vaccination = new Vaccination();
        vaccination.setEmail(TEST_EMAIL);
        vaccination.setPetName(TEST_PET_NAME);
        vaccination.setRabies(true); // 1 for vaccinated
        vaccination.setDistemper(false); // 0 for not vaccinated
        vaccination.setCanineParvovirus(true);
        vaccination.setBordetella(true);
        vaccination.setLymeDisease(false);
        vaccination.setPanleukopenia(true);
        vaccination.setCaliciVirus(false);
        vaccination.setImmunodeficiencyVirus(true);
        vaccination.setChlamydiaFelis(false);
        vaccination.setLeukemiaVirus(true);
        return vaccination;
    }

    //Mock Medical Condition Details
    private MedicalCondition createMockMedicalCondition() {
        // Setup medical condition data
        MedicalCondition medicalCondition = new MedicalCondition();
        medicalCondition.setEmail(TEST_EMAIL);
        medicalCondition.setPetName(TEST_PET_NAME);
        medicalCondition.setCondition("Obesity");
        return medicalCondition;
    }

    //Mock Treatment Plan Details
    private TreatmentPlan createMockTreatmentPlan() {
        // Setup treatment plan data
        TreatmentPlan treatmentPlan = new TreatmentPlan();
        treatmentPlan.setEmail(TEST_EMAIL);
        treatmentPlan.setPetName(TEST_PET_NAME);
        treatmentPlan.setTreatmentName("Insulin Injection");
        treatmentPlan.setTreatmentDate(LocalDate.now());
        return treatmentPlan;

    }

//------------------------------------------------- Add Medical Report Tests ------------------------------------------------
@Test
    public void addingMedicalReport_Success() throws Exception {
        User validUser = new User(1L, "John Doe", "[user@example.com](mailto:user@example.com)", "password123");
        Mockito.when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(validUser);


        String sessionToken = "mock-session-token";
        Map<String, String> sessionTokens = new HashMap<>();
        sessionTokens.put(sessionToken, TEST_EMAIL);

        // Create mocks
        Medical medicalReport = createMockMedical();
        Vaccination vaccination = createMockVaccination();
        MedicalCondition medicalCondition = createMockMedicalCondition();
        TreatmentPlan treatmentPlan = createMockTreatmentPlan();

        // Mock the services
        when(loginController.getSessionTokens()).thenReturn(sessionTokens);

        // Mock save responses
        Mockito.when(medicalRepository.save(Mockito.any(Medical.class))).thenReturn(medicalReport);
        Mockito.when(vaccinationRepository.save(Mockito.any(Vaccination.class))).thenReturn(vaccination);
        Mockito.when(medicalConditionRepository.save(Mockito.any(MedicalCondition.class))).thenReturn(medicalCondition);
        Mockito.when(treatmentPlanRepository.save(Mockito.any(TreatmentPlan.class))).thenReturn(treatmentPlan);

        // Create a DTO to send all the necessary data
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("medical", medicalReport);
        requestData.put("vaccination", vaccination);
        requestData.put("medicalCondition", medicalCondition);
        requestData.put("treatmentPlan", treatmentPlan);

        // Simulate POST request
        mockMvc.perform(post("/addReport")
                .param("sessionToken", sessionToken)  // Include session token in the request
                .param("email", medicalReport.getEmail())
                .param("petName", medicalReport.getPetName())
                .param("petAge", String.valueOf(medicalReport.getPetAge()))
                .param("petSex", medicalReport.getPetSex())
                .param("petWeight", String.valueOf(medicalReport.getPetWeight()))
                .param("petType", medicalReport.getPetType())
                .param("petBreed", medicalReport.getPetBreed())

                // Add vaccination parameters
                .param("rabies", String.valueOf(vaccination.isRabies()))
                .param("distemper", String.valueOf(vaccination.isDistemper()))
                .param("canineParvovirus", String.valueOf(vaccination.isCanineParvovirus()))
                .param("bordetella", String.valueOf(vaccination.isBordetella()))
                .param("lymeDisease", String.valueOf(vaccination.isLymeDisease()))
                .param("panleukopenia", String.valueOf(vaccination.isPanleukopenia()))
                .param("caliciVirus", String.valueOf(vaccination.isCaliciVirus()))
                .param("immunodeficiencyVirus", String.valueOf(vaccination.isImmunodeficiencyVirus()))
                .param("chlamydiaFelis", String.valueOf(vaccination.isChlamydiaFelis()))
                .param("leukemiaVirus", String.valueOf(vaccination.isLeukemiaVirus()))

                .param("medical_condition", medicalCondition.getCondition())

                .param("treatmentName", treatmentPlan.getTreatmentName())
                .param("treatmentDate", treatmentPlan.getTreatmentDate().toString()) 
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))  
                .andExpect(status().is3xxRedirection())  
                .andExpect(redirectedUrl("/medical"));
    }

//------------------------------------------------- View Medical Report Test ------------------------------------------------
@Test
    public void showMedicalReport_Success() throws Exception {
        String sessionToken = "mock-session-token";
        Map<String, String> sessionTokens = new HashMap<>();
        sessionTokens.put(sessionToken, TEST_EMAIL);

        Mockito.when(loginController.getSessionTokens()).thenReturn(sessionTokens);

        // Mock the medical service to return a list of medical records
        List<Medical> medicalRecords = Collections.singletonList(createMockMedical());
        Mockito.when(medicalService.getMedicalRecordsByEmail(TEST_EMAIL)).thenReturn(medicalRecords);
        Mockito.when(medicalService.getMedicalRecordByEmailAndPetName(TEST_EMAIL, TEST_PET_NAME)).thenReturn(createMockMedical());
        Mockito.when(vaccinationService.getVaccinationRecordByEmailAndPetName(TEST_EMAIL, TEST_PET_NAME)).thenReturn(createMockVaccination());
        Mockito.when(medicalConditionService.getMedicalConditionsByEmailAndPetName(TEST_EMAIL, TEST_PET_NAME)).thenReturn(Collections.singletonList(createMockMedicalCondition()));
        Mockito.when(treatmentPlanService.getTreatmentPlansByEmailAndPetName(TEST_EMAIL, TEST_PET_NAME)).thenReturn(Collections.singletonList(createMockTreatmentPlan()));

        mockMvc.perform(get("/medical")
                .param("sessionToken", sessionToken)
                .param("petName", TEST_PET_NAME))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("medicalRecord"))
                .andExpect(model().attributeExists("vaccinationRecord"))
                .andExpect(model().attributeExists("medicalConditions"))
                .andExpect(model().attributeExists("treatmentPlan"))
                .andExpect(view().name("medical"));
    }
}