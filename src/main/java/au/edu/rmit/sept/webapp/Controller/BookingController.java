package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;



import java.time.LocalDate;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    

    // New GetMapping for the clinicSelector page
    @GetMapping("/selectClinic")
    public String selectClinic() {
        return "clinicSelector"; // This directs to the clinicSelector.html page
    }

    @GetMapping("/makebooking")
    public String makeBooking(@RequestParam("clinicName") String clinicName, Model model) {
        model.addAttribute("clinicName", clinicName); // Pass the selected clinic to the makeBooking page
        return "makeBooking"; 
    }

    // Endpoint to get available time slots for a given date
    @GetMapping("/availableTimeSlots")
    @ResponseBody
    public List<String> getAvailableTimeSlots(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam("clinicName") String clinicName){
        // Call the service method to get available time slots for the selected date
        return bookingService.getAvailableTimeSlots(date, clinicName);
    }

    @PostMapping("/createBooking")
    public String createBooking(
            @RequestParam("bookingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate bookingDate,
            @RequestParam("timeSlot") String timeSlot,
            @RequestParam("clinicName") String clinicName,
            @RequestParam("userEmail") String userEmail,
            RedirectAttributes redirectAttributes) {

            System.out.println("Creating booking for userEmail: " + userEmail);

        // Debugging Statements
        System.out.println("Received booking request for date: " + bookingDate);
        System.out.println("Time Slot: " + timeSlot);
        System.out.println("Clinic Name: " + clinicName);
        System.out.println("User Email: " + userEmail);

        // Check if the time slot is already booked
        if (bookingService.isTimeSlotAvailable(bookingDate, timeSlot, clinicName)) {
            // Save the booking if the time slot is available
            bookingService.createBooking(bookingDate, timeSlot, clinicName, userEmail);
            return "redirect:/bookings";
        } else {
            // Redirect back to the make booking page with an error message if the time slot is taken
            redirectAttributes.addFlashAttribute("error", "Time slot is already booked.");
            return "redirect:/makebooking";
        }
    }

    @GetMapping("/rescheduleBooking/{id}")
    public String rescheduleBooking(@PathVariable Long id) {
        // Delete the existing booking by ID
        bookingService.deleteBookingById(id);

        // Redirect to the make booking page
        return "redirect:/clinicSelector";
    }

    @GetMapping("/cancelBooking/{id}")
    public String cancelBooking(@PathVariable("id") Long bookingId, RedirectAttributes redirectAttributes) {
        // Call the service to delete the booking
        bookingService.deleteBooking(bookingId);

        // Add a flash message for confirmation (optional)
        redirectAttributes.addFlashAttribute("message", "Booking cancelled successfully!");

        // Redirect to the bookings page
        return "redirect:/bookings";
    }
}
