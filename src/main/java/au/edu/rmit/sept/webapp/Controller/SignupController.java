package au.edu.rmit.sept.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.model.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;
import au.edu.rmit.sept.webapp.service.EmailService;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;  // Inject the email service

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@RequestParam String fullname, @RequestParam String email,
                             @RequestParam String password, @RequestParam String confirmPassword,
                             Model model) {

        // Check if the email is already registered
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            model.addAttribute("errorMessage", "This email is already in use. Please choose a different email.");
            return "signup";
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match. Please try again.");
            return "signup";
        }

        // Check if fullname is empty
        if (fullname == null || fullname.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Fullname is required.");
            return "signup"; // Stay on the signup page with error message
        }

        // Check password length (minimum)
        if (password.length() < 8) {
            model.addAttribute("errorMessage", "Password must be at least 8 characters.");
            return "signup"; // Stay on the signup page with error message
        }

        // Check password length (maximum)
        if (password.length() > 100) {
            model.addAttribute("errorMessage", "Password must be at most 100 characters.");
            return "signup"; // Stay on the signup page with error message
        }

        // Create a new user and save it to the repository
        User newUser = new User(null, fullname, email, password);
        userRepository.save(newUser);

        // Send confirmation email
        emailService.sendSignupConfirmationEmail(email, fullname);

        // Redirect to the login page with a success message
        return "redirect:/login?signupSuccess=true";
    }
}
