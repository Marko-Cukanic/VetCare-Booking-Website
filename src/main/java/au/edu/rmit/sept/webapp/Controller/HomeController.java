package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Booking;
import au.edu.rmit.sept.webapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LoginController loginController;

    @GetMapping("/")
    public String home(@RequestParam(value = "loginSuccess", required = false) String loginSuccess, 
                       @RequestParam(value = "sessionToken", required = false) String sessionToken, 
                       Model model) {
        if ("true".equals(loginSuccess)) {
            model.addAttribute("successMessage", "You have successfully logged in!");
        }

        // Check if the user is logged in by verifying the session token
        Map<String, String> sessionTokens = loginController.getSessionTokens();
        String userEmail = sessionTokens.get(sessionToken);

        if (userEmail == null) {
            // User is not logged in
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("bookings", null);
            model.addAttribute("error", "Please log in to view bookings.");
        } else {
            // User is logged in
            model.addAttribute("isLoggedIn", true);

            // Fetch the list of bookings for the logged-in user
            List<Booking> bookings = bookingService.getBookingsByUserEmail(userEmail);
            model.addAttribute("bookings", bookings);  // Pass bookings to the home page
        }

        return "home";  // Serves the home page view
    }
}
