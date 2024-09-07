package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyBookingsController {

    @GetMapping("/bookings")
    public String makeBooking() {
        return "bookings"; // This should match the name of your HTML file in the templates directory
    }
}
