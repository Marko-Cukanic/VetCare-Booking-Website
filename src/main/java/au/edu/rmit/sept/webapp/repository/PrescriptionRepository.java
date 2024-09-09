package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.model.Prescription; // Import your Prescription model
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}

