package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.PrescriptionHistory;
import au.edu.rmit.sept.webapp.service.PrescriptionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PrescriptionHistoryController {

    @Autowired
    private PrescriptionHistoryService prescriptionHistoryService;

    @GetMapping("/prescription-history")
    public String getPrescriptionHistory(Model model) {
        List<PrescriptionHistory> histories = prescriptionHistoryService.getAllPrescriptionHistories();
        model.addAttribute("histories", histories);
        return "prescription-history";
    }
}
