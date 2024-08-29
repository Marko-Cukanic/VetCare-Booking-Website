package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrescriptionController {

    @GetMapping("/prescriptions")
    public String showPrescriptions() {
        return "prescriptions";
    }
}
