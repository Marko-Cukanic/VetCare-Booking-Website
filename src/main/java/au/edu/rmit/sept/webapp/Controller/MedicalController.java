package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MedicalController {

    @GetMapping("/medical")
    public String showMedicalHistory() {
        return "medical";  // This returns the Thymeleaf template "medical.html"
    }
}
