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
public class MyBookingsController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LoginController loginController; // Assume you have a LoginController for session tokens

    // Get all bookings and display them on the bookings page with session token verification
    @GetMapping("/bookings")
    public String getBookings(@RequestParam(required = false) String sessionToken, Model model) {
        // Verify session token to check if the user is logged in
        Map<String, String> sessionTokens = loginController.getSessionTokens();
        if (sessionToken == null || !sessionTokens.containsKey(sessionToken)) {
            model.addAttribute("isLoggedIn", false);
            return "bookings";  // Show not logged-in page with a prompt
        }

        // User is logged in
        model.addAttribute("isLoggedIn", true);

        // Fetch all bookings and display them (not filtered by user)
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings); // Pass bookings to the view

        return "bookings"; // Ensure this points to the correct Thymeleaf template
    }
}

