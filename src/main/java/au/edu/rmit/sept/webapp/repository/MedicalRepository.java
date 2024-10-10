package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.model.Medical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRepository extends JpaRepository<Medical, Long> {
    List<Medical> findByEmail(String email);  // Get all pets by email
    List<Medical> findByEmailAndPetName(String email, String petName);  // Get medical record by pet name and email

    boolean existsByPetID(Long long1);  // Check for pet id
    boolean existsByEmailAndPetName(String email, String petName);  // Check for name with email
}

