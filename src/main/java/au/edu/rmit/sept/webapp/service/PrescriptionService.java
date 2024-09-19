package au.edu.rmit.sept.webapp.service;


import au.edu.rmit.sept.webapp.model.Prescription;
import au.edu.rmit.sept.webapp.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> getPrescriptionsByEmail(String email) {
        return prescriptionRepository.findByEmail(email);
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id).orElse(null);
    }

    public void savePrescription(Prescription prescription) {
        prescriptionRepository.save(prescription);
    }
}
