package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Prescription;
import au.edu.rmit.sept.webapp.model.PrescriptionHistory;
import au.edu.rmit.sept.webapp.service.PrescriptionService;
import au.edu.rmit.sept.webapp.service.PrescriptionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PrescriptionController {

    @Autowired
    private PrescriptionHistoryService prescriptionHistoryService;

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/prescriptions")
    public String getPrescriptionsAndHistory(Model model) {
        // Fetch prescription histories
        List<PrescriptionHistory> histories = prescriptionHistoryService.getAllPrescriptionHistories();
        model.addAttribute("histories", histories);

        // Fetch prescriptions
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);

        return "prescriptions";  // Return the correct template
    }

    
}

