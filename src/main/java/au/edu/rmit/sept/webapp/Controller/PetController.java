package au.edu.rmit.sept.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import au.edu.rmit.sept.webapp.model.Medical;
import au.edu.rmit.sept.webapp.repository.MedicalRepository;
import au.edu.rmit.sept.webapp.service.MedicalService;

import java.util.List;

@Controller
public class PetController {

    @Autowired
    private MedicalRepository medicalRepository;

    @Autowired
    private MedicalService medicalService; // Inject the MedicalService instance

    @GetMapping("/mypets")
    public String myPetsPage(@RequestParam(value = "sessionToken", required = false) String sessionToken, Model model) {
        // Retrieve the email of the logged-in user using the session token
        String email = getEmailFromSessionToken(sessionToken);

        // Fetch the pets associated with this user
        List<Medical> pets = medicalRepository.findByEmail(email);
        model.addAttribute("pets", pets);

        return "mypets";
    }

    @GetMapping("/addPet")
    public String showAddPetForm(@RequestParam String sessionToken, Model model) {
        // Retrieve the email from the session
        String email = getEmailFromSessionToken(sessionToken);
        
        // Initialize the model for the form
        model.addAttribute("medical", new Medical());
        model.addAttribute("email", email);
        
        return "addPet";
    }

    @PostMapping("/addPet")
    public String addPet(@ModelAttribute Medical medical, @RequestParam String email, Model model) {
        try {
            medicalService.saveMedicalRecord(medical); // Use the instance method correctly
            return "redirect:/mypets"; // Redirect to the pets page after submission
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("email", email);
            return "addPet"; // Return to the form with an error message
        }
    }

    private String getEmailFromSessionToken(String sessionToken) {
        // Implement your logic to fetch email associated with session token
        // Returning a placeholder email for now
        return "user@example.com";
    }
}
