package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Additional query methods (if needed) can be defined here
}
