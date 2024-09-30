package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Clinic;
import au.edu.rmit.sept.webapp.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll(); // Fetch all clinics
    }
}
