package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Medical;
import au.edu.rmit.sept.webapp.repository.MedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalService {

    @Autowired
    private MedicalRepository medicalRepository;

    public List<Medical> getMedicalRecordsByEmail(String email) {
        return medicalRepository.findByEmail(email);  // Fetch all medical records for the user
    }

    public Medical getMedicalRecordByEmailAndPetName(String email, String petName) {
        // Fetch a single medical record based on email and pet name
        return medicalRepository.findByEmailAndPetName(email, petName)
                .stream().findFirst().orElse(null);
    }
}
