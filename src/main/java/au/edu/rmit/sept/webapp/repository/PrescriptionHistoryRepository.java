package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.model.PrescriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionHistoryRepository extends JpaRepository<PrescriptionHistory, Long> {
}