package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Medical;
import au.edu.rmit.sept.webapp.model.Vaccination;
import au.edu.rmit.sept.webapp.model.MedicalCondition;

import au.edu.rmit.sept.webapp.service.MedicalService;
import au.edu.rmit.sept.webapp.service.VaccinationService;
import au.edu.rmit.sept.webapp.service.MedicalConditionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MedicalController {

    @Autowired
    private LoginController loginController; 

    @Autowired
    private MedicalService medicalService; 

    @Autowired
    private VaccinationService vaccinationService; 

    @Autowired
    private MedicalConditionService medicalConditionService;

    //Getting Record Data
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

        // If a petName is provided, fetch its medical details
        if (petName != null && !petName.isEmpty()) {
            Medical medicalRecord = medicalService.getMedicalRecordByEmailAndPetName(email, petName);
            var vaccinationRecord = vaccinationService.getVaccinationRecordByEmailAndPetName(email, petName);
            var medicalConditions = medicalConditionService.getMedicalConditionsByEmailAndPetName(email, petName);
            
            model.addAttribute("medicalConditions", medicalConditions);
            model.addAttribute("medicalRecord", medicalRecord);
            model.addAttribute("vaccinationRecord", vaccinationRecord);

        } else {
            model.addAttribute("medicalRecord", null);  // Handle case when no pet is selected
           model.addAttribute("vaccinationRecord", null); 
           model.addAttribute("medicalConditions", null); 
        }

        model.addAttribute("selectedPetName", petName); // Pass selected pet name to the view
    
        return "medical";
    }
    
    //Adding Record Data
    @GetMapping("/addReport")
    public String showAddReportForm(Model model) {
        model.addAttribute("medical", new Medical());
        model.addAttribute("vaccination", new Vaccination());
        model.addAttribute("medicalCondition", new MedicalCondition());
        return "addReport";
    }

    @PostMapping("/addReport")
    public String addReport(@ModelAttribute Medical medical, 
                            @ModelAttribute Vaccination vaccination,
                            @RequestParam String email,
                            @RequestParam String petName,
                            @RequestParam List<String> medical_condition) {

        // Save the medical record
        medicalService.saveMedicalRecord(medical);

        // Save the vaccination record
        vaccination.setEmail(medical.getEmail());
        vaccination.setPetName(medical.getPetName());
        vaccinationService.saveVaccinationRecord(vaccination);
        
        // Save each medical condition from the form
        for (String condition : medical_condition) {
            if (!condition.isEmpty()) {  // Avoid saving empty conditions
                MedicalCondition medicalCondition = new MedicalCondition();
                medicalCondition.setEmail(email);
                medicalCondition.setPetName(petName);
                medicalCondition.setCondition(condition);
                medicalConditionService.saveMedicalCondition(medicalCondition);
            }
        }
            
        return "redirect:/medical"; // Redirect to medical records page after submission
    }
}