package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.PetController;
import au.edu.rmit.sept.webapp.model.Medical;
import au.edu.rmit.sept.webapp.repository.MedicalRepository;
import au.edu.rmit.sept.webapp.service.MedicalConditionService;
import au.edu.rmit.sept.webapp.service.MedicalService;
import au.edu.rmit.sept.webapp.service.TreatmentPlanService;
import au.edu.rmit.sept.webapp.service.VaccinationService;
import au.edu.rmit.sept.webapp.controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRepository medicalRepository;

    @MockBean
    private MedicalService medicalService;

    @MockBean
    private VaccinationService vaccinationService; // Add missing mock

    @MockBean
    private MedicalConditionService medicalConditionService; // Add missing mock

    @MockBean
    private TreatmentPlanService treatmentPlanService; // Add missing mock

    @MockBean
    private LoginController loginController;

    private List<Medical> mockPets;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock session token and email
        when(loginController.getSessionTokens()).thenReturn(Map.of("validToken", "user@example.com"));

        // Prepare mock data for testing
        mockPets = new ArrayList<>();
        Medical pet1 = new Medical();
        pet1.setPetName("Buddy");
        pet1.setPetBreed("Labrador");
        pet1.setPetAge(3);
        pet1.setPetType("Dog");
        pet1.setPetWeight(30); // Changed to int
        pet1.setPetSex("Male");
        pet1.setEmail("user@example.com");
        mockPets.add(pet1);

        Medical pet2 = new Medical();
        pet2.setPetName("Whiskers");
        pet2.setPetBreed("Siamese");
        pet2.setPetAge(2);
        pet2.setPetType("Cat");
        pet2.setPetWeight(6); // Changed to int
        pet2.setPetSex("Female");
        pet2.setEmail("user@example.com");
        mockPets.add(pet2);
    }

    @Test
    void testMyPetsPageWithValidSessionToken() throws Exception {
        // Mock the repository call to return pets
        when(medicalRepository.findByEmail("user@example.com")).thenReturn(mockPets);

        mockMvc.perform(get("/mypets").param("sessionToken", "validToken"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pets"))
                .andExpect(model().attribute("pets", mockPets))
                .andExpect(view().name("mypets"));
    }

    @Test
    void testMyPetsPageWithInvalidSessionToken() throws Exception {
        mockMvc.perform(get("/mypets").param("sessionToken", "invalidToken"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pets"))
                .andExpect(model().attribute("pets", new ArrayList<>())) // No pets should be returned
                .andExpect(view().name("mypets"));
    }

    @Test
    void testAddPetSuccess() throws Exception {
        // Mock the saveMedicalRecord() method to do nothing (since it's void)
        doNothing().when(medicalService).saveMedicalRecord(any(Medical.class));

        mockMvc.perform(post("/addPet")
                .param("sessionToken", "validToken")
                .param("petName", "Buddy")
                .param("petBreed", "Labrador")
                .param("petAge", "3")
                .param("petType", "Dog")
                .param("petWeight", "30")
                .param("petSex", "Male"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mypets?sessionToken=validToken"));
    }

    @Test
    void testDeletePetSuccess() throws Exception {
        // Mock the repository call to find a pet by ID
        Medical petToDelete = mockPets.get(0);
        when(medicalRepository.findById(1L)).thenReturn(java.util.Optional.of(petToDelete));

        // Mock the delete operation
        doNothing().when(medicalRepository).deleteById(1L);

        mockMvc.perform(post("/deletePet")
                .param("id", "1")
                .param("sessionToken", "validToken"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mypets?sessionToken=validToken"));
    }

   
}
