package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.PrescriptionHistory;
import au.edu.rmit.sept.webapp.repository.PrescriptionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionHistoryService {

    @Autowired
    private PrescriptionHistoryRepository prescriptionHistoryRepository;

    public List<PrescriptionHistory> getAllPrescriptionHistories() {
        return prescriptionHistoryRepository.findAll();
    }

    
}
