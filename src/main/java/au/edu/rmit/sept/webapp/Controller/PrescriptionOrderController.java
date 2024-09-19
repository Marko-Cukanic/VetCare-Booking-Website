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

    @Autowired  // Add this to inject the PrescriptionHistoryService
    private PrescriptionHistoryService prescriptionHistoryService;  // Add this line

    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam("medication") Long prescriptionId, 
                            @RequestParam("quantity") int quantity) {

        // Retrieve the selected prescription using the prescription ID
        Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
        
        // Create a new PrescriptionHistory object
        PrescriptionHistory history = new PrescriptionHistory();
        history.setMedicationName(prescription.getMedicationName());
        history.setPetName(prescription.getPetName());
        history.setStartDate(prescription.getPrescriptionDate());  // Assuming the prescription date is the start date
        history.setEndDate(null);  // You can set an appropriate end date if needed
        history.setVetName(prescription.getVetName());

        // Save the new history record to the prescription_history table
        prescriptionHistoryService.savePrescriptionHistory(history);

        // (Optional) Mark the prescription as ordered or update its status if needed

        // Redirect back to the prescription listing page to show the updated data
        return "redirect:/prescriptions";
    }

    @PostMapping("/finaliseOrder")
    public String finaliseOrder(@RequestParam("medicationId") Long medicationId, 
                                @RequestParam("quantity") int quantity, Model model) {
        // Fetch the selected prescription
        Prescription prescription = prescriptionService.getPrescriptionById(medicationId);

        // Add prescription details to the model
        model.addAttribute("prescription", prescription);
        model.addAttribute("quantity", quantity);

        // Ensure that you return "orderSuccess" to match the template name
        return "orderSuccess";  // This should match the name of the HTML file
    }
}
