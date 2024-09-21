package au.edu.rmit.sept.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class TreatmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String petName;
    private String treatmentName;
    private LocalDate treatmentDate;

    // Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPetName() {
        return petName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public void setTreatmentDate(LocalDate treatmentDate) {
        this.treatmentDate = treatmentDate;
    }
}
