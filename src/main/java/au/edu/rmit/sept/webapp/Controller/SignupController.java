package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@RequestParam String fullname, @RequestParam String email,
                             @RequestParam String password, @RequestParam String confirmPassword) {
        // Implement your signup logic here
        // For example, check if passwords match, and save the user to the database

        if (!password.equals(confirmPassword)) {
            // Handle the error (e.g., return a view with an error message)
            return "signup"; // Ideally, pass an error message to the view
        }

        // Save the user to the database (assuming you have a User entity and repository)

        return "redirect:/login";  // Redirect to the login page after successful signup
    }
}
