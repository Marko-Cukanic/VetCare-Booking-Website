package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Clinic;
import au.edu.rmit.sept.webapp.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping("/clinicSelector")
    public String showClinicSelector(Model model) {
        // Fetch clinics from the database
        List<Clinic> clinics = clinicService.getAllClinics();

        // Add the clinics to the model
        model.addAttribute("clinics", clinics);

        // Return the clinicSelector view
        return "clinicSelector";
    }
}
