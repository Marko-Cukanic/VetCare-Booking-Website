package au.edu.rmit.sept.webapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {

    @GetMapping("/makebooking")
    public String makeBooking() {
        return "makeBooking"; // Ensure the template name is correct
    }
}
