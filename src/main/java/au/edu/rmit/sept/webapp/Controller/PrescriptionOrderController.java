package au.edu.rmit.sept.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import au.edu.rmit.sept.webapp.service.PrescriptionService;
import au.edu.rmit.sept.webapp.model.Prescription;

@Controller
public class PrescriptionOrderController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/confirmOrder")  // Camel case URL mapping
    public String confirmOrder(@RequestParam("medication") Long medicationId, 
                               @RequestParam("quantity") int quantity, Model model) {
        // Fetch the selected prescription from the database
        Prescription prescription = prescriptionService.getPrescriptionById(medicationId);
        
        // Add prescription and quantity to the model to pass to the confirmation page
        model.addAttribute("prescription", prescription);
        model.addAttribute("quantity", quantity);

        return "confirmOrder";  // Forward to the confirmation page
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
