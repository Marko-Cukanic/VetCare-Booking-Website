package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.MedicalCondition;
import au.edu.rmit.sept.webapp.repository.MedicalConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalConditionService {

    @Autowired
    private MedicalConditionRepository medicalConditionRepository;

    public List<MedicalCondition> getMedicalConditionsByEmailAndPetName(String email, String petName) {
        return medicalConditionRepository.findByEmailAndPetName(email, petName);
    }

    public void saveMedicalCondition(MedicalCondition medicalCondition) {
        medicalConditionRepository.save(medicalCondition);
    }
}
