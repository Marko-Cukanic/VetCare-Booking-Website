package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Prescription; // Import Prescription model
import au.edu.rmit.sept.webapp.repository.PrescriptionRepository; // Import PrescriptionRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid prescription ID: " + id));
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public void savePrescription(Prescription prescription) {
        prescriptionRepository.save(prescription);
    }
}