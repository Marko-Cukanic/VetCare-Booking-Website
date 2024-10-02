package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Find bookings by date and clinic
    List<Booking> findByBookingDateAndClinicName(LocalDate bookingDate, String clinicName);
}
