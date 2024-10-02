package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Clinic;
import au.edu.rmit.sept.webapp.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll(); // Fetch all clinics
    }

    // Method to initialize clinic data at startup
    @PostConstruct
    public void initClinicData() {
        // Check if clinics are already present
        if (clinicRepository.count() == 0) {
            List<Clinic> clinics = Arrays.asList(
                new Clinic("Sunshine Vet Clinic", 50.0, 4.3),
                new Clinic("Pawssum", 139.0, 4.5),
                new Clinic("Lort Smith Animal Hospital", 150.0, 4.2),
                new Clinic("Essendon Veterinary Clinic", 140.0, 4.4),
                new Clinic("Maribyrnong Veterinary Clinic", 160.0, 4.7),
                new Clinic("Delahey Veterinary Clinic", 140.0, 4.4)
            );
            clinicRepository.saveAll(clinics);
        }
    }
}
