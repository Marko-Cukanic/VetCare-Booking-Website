package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.service.BookingService;
import au.edu.rmit.sept.webapp.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/makebooking")
    public String makeBooking() {
        return "makeBooking"; // This should match the name of your HTML file in the templates directory
    }

    // New POST mapping to handle the booking creation
    @PostMapping("/createBooking")
    public String createBooking(
            @RequestParam("bookingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate bookingDate,
            @RequestParam("timeSlot") String timeSlot) {

        // Save the booking with the selected date and time slot
        bookingService.createBooking(bookingDate, timeSlot);

        // Redirect to bookings.html after booking is created
        return "redirect:/bookings"; 
    }
}
