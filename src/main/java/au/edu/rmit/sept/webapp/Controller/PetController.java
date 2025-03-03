package au.edu.rmit.sept.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.edu.rmit.sept.webapp.model.Medical;
import au.edu.rmit.sept.webapp.repository.MedicalRepository;
import au.edu.rmit.sept.webapp.service.MedicalService;

import au.edu.rmit.sept.webapp.service.VaccinationService;
import au.edu.rmit.sept.webapp.service.MedicalConditionService;
import au.edu.rmit.sept.webapp.service.TreatmentPlanService;


import java.util.stream.Collectors;


import java.util.List;
import java.util.Map;

@Controller
public class PetController {

    @Autowired
    private VaccinationService vaccinationService;
    
    @Autowired
    private MedicalConditionService medicalConditionService;
    
    @Autowired
    private TreatmentPlanService treatmentPlanService;

    @Autowired
    private MedicalRepository medicalRepository;

    @Autowired
    private MedicalService medicalService; // Inject the MedicalService instance

    @Autowired
    private LoginController loginController; // Inject the LoginController


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
        System.out.println("Received session token: " + sessionToken);
        String email = getEmailFromSessionToken(sessionToken);
        
        // Add session token to the model
        model.addAttribute("sessionToken", sessionToken);
        
        // Initialize the model for the form
        model.addAttribute("medical", new Medical());
        model.addAttribute("email", email);
        
        return "addPet";
    }

    @PostMapping("/addPet")
    public String addPet(@ModelAttribute Medical medical, 
                        @RequestParam String sessionToken, 
                        Model model) {
        try {
            // Retrieve the email from the session token
            String email = getEmailFromSessionToken(sessionToken);
            if (email == null) {
                model.addAttribute("error", "Invalid session. Please log in again.");
                return "addPet";
            }

            // Associate the email with the pet record
            medical.setEmail(email);

            // Auto-assign a pet ID
            medical.setPetID(generatePetID());
            medicalService.saveMedicalRecord(medical);
            return "redirect:/mypets?sessionToken=" + sessionToken; // Redirect to the pets page after submission
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "addPet"; // Return to the form with an error message
        }
    }
    

    private Long generatePetID() {
        // Generate a 15-digit unique ID. This is a simple implementation; adjust as needed.
        return (long) (Math.random() * 1_000_000_000_000_000L);
    }

    private String getEmailFromSessionToken(String sessionToken) {
        System.out.println("Session Token Received: " + sessionToken);
        Map<String, String> sessionTokens = loginController.getSessionTokens();
        String email = sessionTokens.get(sessionToken);
        if (email == null) {
            System.out.println("No email found for session token: " + sessionToken);
        } else {
            System.out.println("Email Retrieved: " + email);
        }
        return email;
    }

            @GetMapping("/getEmails")
        @ResponseBody
        public List<String> getEmails() {
            // Get all medical records and extract distinct emails
            List<Medical> allRecords = medicalRepository.findAll();
            return allRecords.stream()
                            .map(Medical::getEmail)
                            .distinct()
                            .collect(Collectors.toList());
        }

    

        @GetMapping("/getAllPets")
        @ResponseBody
        public List<Medical> getAllPets() {
            // Fetch all pets from the database
            return medicalRepository.findAll();
        }

        @PostMapping("/deletePet")
public String deletePet(@RequestParam Long id, 
                        @RequestParam String sessionToken, 
                        Model model) {
    try {
        // Fetch the pet record by the primary key 'id'
        Medical petToDelete = medicalRepository.findById(id).orElse(null);

        if (petToDelete == null) {
            model.addAttribute("error", "Pet not found.");
            return "redirect:/mypets?sessionToken=" + sessionToken;
        }

        // Get the email and pet name
        String email = petToDelete.getEmail();
        String petName = petToDelete.getPetName();

        // Delete related records
        vaccinationService.deleteVaccinationsByEmailAndPetName(email, petName);
        medicalConditionService.deleteMedicalConditionsByEmailAndPetName(email, petName);
        treatmentPlanService.deleteTreatmentPlansByEmailAndPetName(email, petName);

        // Delete the pet record itself
        medicalRepository.deleteById(id);

        return "redirect:/mypets?sessionToken=" + sessionToken; // Redirect with session token
    } catch (Exception e) {
        model.addAttribute("error", e.getMessage());
        return "redirect:/mypets?sessionToken=" + sessionToken; // Return with error and session token
    }
}




        
}
