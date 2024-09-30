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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class PrescriptionOrderController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PrescriptionHistoryService prescriptionHistoryService;

    // Step 1: Confirm the order
    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam("medication") Long prescriptionId, 
                               @RequestParam("quantity") int quantity, 
                               Model model) {

        // Retrieve the selected prescription using the prescription ID
        Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);

        // Add prescription and quantity details to the model for confirmation
        model.addAttribute("prescription", prescription);
        model.addAttribute("quantity", quantity);

        // Redirect to the confirmOrder page for user confirmation
        return "confirmOrder"; 
    }

        // Step 2: Finalize the order
        @PostMapping("/finaliseOrder")
    public String finaliseOrder(@RequestParam("medicationId") Long medicationId, 
                                @RequestParam("quantity") int quantity, 
                                Model model) {

        // Fetch the selected prescription using the prescription ID
        Prescription prescription = prescriptionService.getPrescriptionById(medicationId);

        // Set 'is_ordered' to true before updating
        prescription.setOrdered(true);

        // Save the updated prescription back to the database
        prescriptionService.savePrescription(prescription);

        String userEmail = prescription.getEmail();  // Ensure the prescription has the email

        // Create a new PrescriptionHistory object
        PrescriptionHistory history = new PrescriptionHistory();
        history.setMedicationName(prescription.getMedicationName());
        history.setPetName(prescription.getPetName());
        history.setStartDate(prescription.getPrescriptionDate());  

        // Set the end date to 3 months after the start date
        LocalDate startDateLocal = prescription.getPrescriptionDate().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateLocal = startDateLocal.plusMonths(3);
        Date endDate = Date.from(endDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        history.setEndDate(endDate);  // Set the end date
        history.setVetName(prescription.getVetName());
        history.setEmail(userEmail);  // Set the email

        history.setQuantity(quantity);

        // Save the new history record to the prescription_history table
        prescriptionHistoryService.savePrescriptionHistory(history);

        // Add prescription details to the model for success message
        model.addAttribute("prescription", prescription);
        model.addAttribute("quantity", quantity);

        // Redirect to the success page after saving the order
        return "orderSuccess";  
    }
}