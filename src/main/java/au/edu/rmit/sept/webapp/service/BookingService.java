package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Booking;
import au.edu.rmit.sept.webapp.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(LocalDate bookingDate, String timeSlot) {
        Booking booking = new Booking(bookingDate, timeSlot);
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll(); // Fetch all bookings from the database
    }

    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }

    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

}
