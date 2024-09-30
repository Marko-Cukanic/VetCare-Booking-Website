package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Booking;
import au.edu.rmit.sept.webapp.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    private static final String[] DOCTORS = {"Dr. Cukanic", "Dr. Saleh", "Dr. Dao", "Dr. Le", "Dr. Thai"};

    // Method to create a booking
    public Booking createBooking(LocalDate bookingDate, String timeSlot, String clinicName) {
        String doctor = getRandomDoctor();

        Booking booking = new Booking(bookingDate, timeSlot, clinicName, doctor);
        return bookingRepository.save(booking);
    }

    private String getRandomDoctor() {
        Random random = new Random();
        int index = random.nextInt(DOCTORS.length); // Random index from 0 to 4
        return DOCTORS[index];
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
    public boolean isTimeSlotAvailable(LocalDate bookingDate, String timeSlot, String clinicName) {
        List<Booking> bookingsOnDateAndClinic = bookingRepository.findByBookingDateAndClinicName(bookingDate, clinicName);
        // Check if any booking has the same time slot on that date
        return bookingsOnDateAndClinic.stream().noneMatch(booking -> booking.getTimeSlot().equals(timeSlot));
    }

    // Method to get available time slots for a given date
    public List<String> getAvailableTimeSlots(LocalDate bookingDate, String clinicName) {
        // All possible time slots
        List<String> allSlots = Arrays.asList(
            "10:00", "10:20", "10:40", "11:00", "11:20", "11:40", "12:00", "12:20", 
            "12:40", "13:00", "13:20", "13:40", "14:00", "14:20", "14:40", "15:00", 
            "15:20", "15:40", "16:00", "16:20", "16:40", "17:00");

        // Fetch existing bookings for the date and clinic
        List<Booking> bookingsOnDateAndClinic = bookingRepository.findByBookingDateAndClinicName(bookingDate, clinicName);

        List<String> unavailableSlots = bookingsOnDateAndClinic.stream()
            .map(Booking::getTimeSlot)
            .collect(Collectors.toList());

        // Return available slots (those not in unavailableSlots)
        List<String> availableSlots = new ArrayList<>(allSlots);
        availableSlots.removeAll(unavailableSlots);

        return availableSlots;
    }
}
