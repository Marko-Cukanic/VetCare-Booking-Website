package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Prescription;
import au.edu.rmit.sept.webapp.model.PrescriptionHistory;
import au.edu.rmit.sept.webapp.service.PrescriptionService;
import au.edu.rmit.sept.webapp.service.PrescriptionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PrescriptionHistoryService prescriptionHistoryService;

    @Autowired
    private LoginController loginController;

    @GetMapping("/prescriptions")
    public String getPrescriptionsAndHistory(@RequestParam(required = false) String sessionToken, Model model) {
        // Verify session token
        Map<String, String> sessionTokens = loginController.getSessionTokens();
        if (sessionToken == null || !sessionTokens.containsKey(sessionToken)) {
            model.addAttribute("isLoggedIn", false);
            return "prescriptions";  // Show not logged in page
        }

        // User is logged in
        String email = sessionTokens.get(sessionToken);
        model.addAttribute("isLoggedIn", true);

        // Fetch prescriptions and history by email
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByEmail(email);
        List<PrescriptionHistory> histories = prescriptionHistoryService.getPrescriptionHistoriesByEmail(email);
        model.addAttribute("prescriptions", prescriptions);
        model.addAttribute("histories", histories);

        boolean isAdmin = email.equals("admin@vetcare.com"); // Example logic for admin check
        model.addAttribute("isAdmin", isAdmin);

        return "prescriptions";  // Render prescriptions template
    }

    @GetMapping("/assignPrescription")
    public String showAssignPrescriptionForm(@RequestParam(required = false) String sessionToken, Model model) {
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("sessionToken", sessionToken);  // Pass the session token for the logout button
        return "assignPrescription";  // Render the 'assignPrescription.html' page
    }

    @PostMapping("/assignPrescription")
    public String assignPrescription(@ModelAttribute("prescription") Prescription prescription, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "assignPrescription";  // Re-render the form if there are validation errors
        }

        prescription.setPrescriptionDate(new Date()); // Set the prescription date to the current date
        prescription.setOrdered(false); // Set isOrdered to false by default when assigning a new prescription

        prescriptionService.savePrescription(prescription); // Save the prescription
        model.addAttribute("successMessage", "Prescription assigned successfully!"); // Add a success message to the model
        model.addAttribute("prescription", new Prescription()); // Clear the form fields

        return "assignPrescription";  // Return the form page again with success message
    }

    
}
