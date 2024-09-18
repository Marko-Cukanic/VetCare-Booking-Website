package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Vaccination;
import au.edu.rmit.sept.webapp.repository.VaccinationRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository; 

    //Getting Records 
    public Vaccination getVaccinationRecordByEmailAndPetName(String email, String petName) {
        return vaccinationRepository.findByEmailAndPetName(email, petName);
    }

    //Saving/adding records
    public void saveVaccinationRecord(Vaccination vaccinationRecord) {
        vaccinationRepository.save(vaccinationRecord);
    }
}
