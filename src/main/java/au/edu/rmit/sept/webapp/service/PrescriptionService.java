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

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }
}