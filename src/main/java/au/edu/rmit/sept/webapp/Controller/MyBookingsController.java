package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Booking;
import au.edu.rmit.sept.webapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyBookingsController {

    @Autowired
    private BookingService bookingService;

    // Get all bookings and display them in the bookings page
    @GetMapping("/bookings")
    public String getBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings); // Pass bookings to the view
        return "bookings"; // Ensure this points to the correct Thymeleaf template
    }
}
