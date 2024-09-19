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

@Controller
public class PrescriptionOrderController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired  // Inject the PrescriptionHistoryService
    private PrescriptionHistoryService prescriptionHistoryService;

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

    @PostMapping("/finaliseOrder")
    public String finaliseOrder(@RequestParam("medicationId") Long medicationId, 
                                @RequestParam("quantity") int quantity, Model model) {
        // Fetch the selected prescription using the prescription ID
        Prescription prescription = prescriptionService.getPrescriptionById(medicationId);

        // Mark the prescription as ordered
        prescription.setOrdered(true);

        // Save the updated prescription back to the database
        prescriptionService.savePrescription(prescription);

        // Create a new PrescriptionHistory object
        PrescriptionHistory history = new PrescriptionHistory();
        history.setMedicationName(prescription.getMedicationName());
        history.setPetName(prescription.getPetName());
        history.setStartDate(prescription.getPrescriptionDate());  // Assuming the prescription date is the start date
        history.setEndDate(null);  // You can set an appropriate end date if needed
        history.setVetName(prescription.getVetName());

        // Save the new history record to the prescription_history table
        prescriptionHistoryService.savePrescriptionHistory(history);

        // Add prescription details to the model for success message
        model.addAttribute("prescription", prescription);
        model.addAttribute("quantity", quantity);

        // Redirect to the success page after saving the order
        return "orderSuccess";  // This should match the name of the success HTML file
    }
}
