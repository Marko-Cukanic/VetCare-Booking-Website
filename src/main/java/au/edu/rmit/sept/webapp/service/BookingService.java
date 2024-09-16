package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Booking;
import au.edu.rmit.sept.webapp.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    // Method to create a booking
    public Booking createBooking(LocalDate bookingDate, String timeSlot) {
        Booking booking = new Booking(bookingDate, timeSlot);
        return bookingRepository.save(booking);
    }

    // Method to fetch all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll(); // Fetch all bookings from the database
    }

    // Method to delete booking by ID
    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }

    // Method to delete a booking
    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    // Method to check if a time slot is available for a given date
    public boolean isTimeSlotAvailable(LocalDate bookingDate, String timeSlot) {
        List<Booking> bookingsOnDate = bookingRepository.findByBookingDate(bookingDate);
        // Check if any booking has the same time slot on that date
        return bookingsOnDate.stream().noneMatch(booking -> booking.getTimeSlot().equals(timeSlot));
    }

    // Method to get available time slots for a given date
    public List<String> getAvailableTimeSlots(LocalDate bookingDate) {
        // All possible time slots
        List<String> allSlots = Arrays.asList(
            "10:00", "10:20", "10:40", "11:00", "11:20", "11:40", "12:00", "12:20", 
            "12:40", "13:00", "13:20", "13:40", "14:00", "14:20", "14:40", "15:00", 
            "15:20", "15:40", "16:00", "16:20", "16:40", "17:00");

        // Fetch existing bookings for the date
        List<Booking> bookingsOnDate = bookingRepository.findByBookingDate(bookingDate);

        // Create a list of unavailable slots based on existing bookings
        List<String> unavailableSlots = new ArrayList<>();
        for (Booking booking : bookingsOnDate) {
            unavailableSlots.add(booking.getTimeSlot());
        }

        // Return available slots (those not in unavailableSlots)
        List<String> availableSlots = new ArrayList<>(allSlots);
        availableSlots.removeAll(unavailableSlots);

        return availableSlots;
    }
}
