package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Clinic;
import au.edu.rmit.sept.webapp.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private LoginController loginController;  // Assuming you have a LoginController managing session tokens

    @GetMapping("/clinicSelector")
    public String showClinicSelector(@RequestParam(required = false) String sessionToken, Model model) {
        // Verify session token
        Map<String, String> sessionTokens = loginController.getSessionTokens();
        if (sessionToken == null || !sessionTokens.containsKey(sessionToken)) {
            model.addAttribute("isLoggedIn", false);
            return "clinicSelector";  // Display login prompt if not logged in
        }

        // User is logged in
        String email = sessionTokens.get(sessionToken);
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userEmail", email);  // Pass the user's email to the view

        // Fetch clinics from the database and add them to the model
        List<Clinic> clinics = clinicService.getAllClinics();
        model.addAttribute("clinics", clinics);

        // Return the clinicSelector view for logged-in users
        return "clinicSelector";
    }
}
