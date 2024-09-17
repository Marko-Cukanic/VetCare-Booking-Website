package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Medical;
import au.edu.rmit.sept.webapp.service.MedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MedicalController {

    @Autowired
    private LoginController loginController; // Autowire LoginController

    @Autowired
    private MedicalService medicalService; // Autowire MedicalService

    @GetMapping("/medical")
    public String showMedicalHistory(@RequestParam(required = false) String sessionToken,
                                     @RequestParam(required = false) String petName, 
                                     Model model) {
    
        // Check if sessionToken is invalid or missing
        Map<String, String> sessionTokens = loginController.getSessionTokens();
        if (sessionToken == null || !sessionTokens.containsKey(sessionToken)) {
            model.addAttribute("isLoggedIn", false);
            return "medical";  // Show the not logged in page
        }
    
        // User is logged in
        String email = sessionTokens.get(sessionToken);
        model.addAttribute("isLoggedIn", true);
    
        // Fetch pet names for the dropdown list
        List<Medical> pets = medicalService.getMedicalRecordsByEmail(email);
        List<String> petNames = pets.stream().map(Medical::getPetName).distinct().toList();
        model.addAttribute("petNames", petNames);
    
        // If a petName is provided, fetch its medical record
        if (petName != null && !petName.isEmpty()) {
            Medical medicalRecord = medicalService.getMedicalRecordByEmailAndPetName(email, petName);
            model.addAttribute("medicalRecord", medicalRecord);
        } else {
            model.addAttribute("medicalRecord", null);  // Handle case when no pet is selected
        }
    
        return "medical";
    }
    
}

