package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.model.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {
    List<MedicalCondition> findByEmailAndPetName(String email, String petName);
}
