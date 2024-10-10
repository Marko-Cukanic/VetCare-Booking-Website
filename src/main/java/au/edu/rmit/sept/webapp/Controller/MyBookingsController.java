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
    private LoginController loginController; // Access session tokens from LoginController

    @GetMapping("/bookings")
    public String getBookings(@RequestParam(required = false) String sessionToken, Model model) {
        // Verify session token to check if the user is logged in
        Map<String, String> sessionTokens = loginController.getSessionTokens();
        String userEmail = sessionTokens.get(sessionToken);

        if (userEmail == null) {
            // User is not logged in
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("error", "Please log in to view your bookings.");
            return "bookings";  // Show not logged-in page with a prompt
        }

        // Debugging statements
        System.out.println("Session Token: " + sessionToken);
        System.out.println("User Email: " + userEmail);

        // User is logged in
        model.addAttribute("isLoggedIn", true);

        // Fetch bookings for the logged-in user and display them
        List<Booking> userBookings = bookingService.getUpcomingBookings(userEmail);
        System.out.println("User Bookings: " + userBookings); // Print the bookings to check if they are being fetched
        model.addAttribute("bookings", userBookings); // Pass user's bookings to the view

        return "bookings"; // Ensure this points to the correct Thymeleaf template
    }

}
