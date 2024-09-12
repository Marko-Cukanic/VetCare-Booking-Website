package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Booking;
import au.edu.rmit.sept.webapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public String home(@RequestParam(value = "loginSuccess", required = false) String loginSuccess, Model model) {
        if ("true".equals(loginSuccess)) {
            model.addAttribute("successMessage", "You have successfully logged in!");
        }

        // Fetch the list of bookings from the BookingService
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);  // Pass bookings to the home page

        return "home";  // Serves the home page view
    }
}