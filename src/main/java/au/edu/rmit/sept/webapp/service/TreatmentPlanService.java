package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.TreatmentPlan;
import au.edu.rmit.sept.webapp.repository.TreatmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TreatmentPlanService {

    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    // Get all treatment plans for a specific pet
    public List<TreatmentPlan> getTreatmentPlansByEmailAndPetName(String email, String petName) {
        return treatmentPlanRepository.findByEmailAndPetName(email, petName);
    }

    // Save treatment plan (add)
    public void saveTreatmentPlan(TreatmentPlan treatmentPlan){
        treatmentPlanRepository.save(treatmentPlan);
    }
}
