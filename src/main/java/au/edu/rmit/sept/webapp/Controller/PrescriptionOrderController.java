package au.edu.rmit.sept.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import au.edu.rmit.sept.webapp.service.PrescriptionService;
import au.edu.rmit.sept.webapp.model.Prescription;
import au.edu.rmit.sept.webapp.model.PrescriptionHistory;
import au.edu.rmit.sept.webapp.service.PrescriptionHistoryService;
import au.edu.rmit.sept.webapp.service.EmailService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class PrescriptionOrderController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PrescriptionHistoryService prescriptionHistoryService;

    @Autowired
    private EmailService emailService;

    // Step 1: Confirm the order
    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam("medication") Long prescriptionId, 
                               @RequestParam("quantity") int quantity, 
                               Model model) {

        Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);

        if (prescription == null) {
            model.addAttribute("errorMessage", "Prescription not found.");
            model.addAttribute("isPrescriptionValid", false);
            return "confirmOrder";
        }

        model.addAttribute("prescription", prescription);
        model.addAttribute("quantity", quantity);
        model.addAttribute("isPrescriptionValid", true);

        return "confirmOrder"; 
    }

    @PostMapping("/finaliseOrder")
    public String finaliseOrder(@RequestParam("medicationId") Long medicationId, 
                                @RequestParam("quantity") int quantity, 
                                Model model) {

        Prescription prescription = prescriptionService.getPrescriptionById(medicationId);

        if (prescription == null) {
            model.addAttribute("errorMessage", "Prescription not found.");
            model.addAttribute("isPrescriptionValid", false);
            return "confirmOrder";
        }

        // Set 'is_ordered' to true before updating
        prescription.setOrdered(true);
        prescriptionService.savePrescription(prescription);

        String userEmail = prescription.getEmail();  // Ensure the prescription has the email

        // Create a new PrescriptionHistory object
        PrescriptionHistory history = new PrescriptionHistory();
        history.setMedicationName(prescription.getMedicationName());
        history.setPetName(prescription.getPetName());
        history.setStartDate(prescription.getPrescriptionDate());

        LocalDate startDateLocal = prescription.getPrescriptionDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateLocal = startDateLocal.plusMonths(3);
        Date endDate = Date.from(endDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        history.setEndDate(endDate);
        history.setVetName(prescription.getVetName());
        history.setEmail(userEmail);
        history.setQuantity(quantity);

        prescriptionHistoryService.savePrescriptionHistory(history);

        // Calculate the price based on quantity and a fixed price per unit (for example, $10 per unit)
        double pricePerUnit = 10.0;
        double totalPrice = quantity * pricePerUnit;

        // Send the order confirmation email
        emailService.sendPrescriptionOrderEmail(userEmail, prescription.getMedicationName(), quantity, totalPrice);

        model.addAttribute("prescription", prescription);
        model.addAttribute("quantity", quantity);
        model.addAttribute("isPrescriptionValid", true);

        return "orderSuccess";  
    }
}
