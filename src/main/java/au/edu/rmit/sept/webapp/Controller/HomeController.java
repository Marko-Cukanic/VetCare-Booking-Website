package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam(value = "loginSuccess", required = false) String loginSuccess, Model model) {
        if ("true".equals(loginSuccess)) {
            model.addAttribute("successMessage", "You have successfully logged in!");
        }
        return "home";  // Serves the home page view
    }


    
}