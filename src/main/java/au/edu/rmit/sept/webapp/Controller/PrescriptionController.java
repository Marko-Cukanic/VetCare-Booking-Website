package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Prescription;
import au.edu.rmit.sept.webapp.model.PrescriptionHistory;
import au.edu.rmit.sept.webapp.service.PrescriptionService;
import au.edu.rmit.sept.webapp.service.PrescriptionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        return "prescriptions";  // Render prescriptions template
    }
}
