package au.edu.rmit.sept.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.edu.rmit.sept.webapp.model.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    private Map<String, String> sessionTokens = new HashMap<>(); // Simulating a token store

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {

        // Check if the user is already logged in
        if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
            return "You are already logged in!";
        }
        // Find the user by email
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            return "Invalid email or password.";
        }

        // Store the user email in the HTTP session upon successful login
        session.setAttribute("userEmail", email);
        session.setAttribute("loggedIn", true); // Set the loggedIn attribute


        // Generate a new session token
        String newSessionToken = UUID.randomUUID().toString();
        sessionTokens.put(newSessionToken, email); // Store the token with the user's email

        // Return the session token to the client
        return newSessionToken;
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(@RequestParam(required = false) String sessionToken, HttpSession session) {
        // Invalidate the session token
        if (sessionToken != null && sessionTokens.containsKey(sessionToken)) {
            sessionTokens.remove(sessionToken);   
        }
        session.invalidate();

        // Return a success message
        return "You have successfully logged out.";
    }

    // Getter for sessionTokens
    public Map<String, String> getSessionTokens() {
        return sessionTokens;
    }
}
