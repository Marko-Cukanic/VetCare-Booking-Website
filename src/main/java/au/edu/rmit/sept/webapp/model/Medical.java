package au.edu.rmit.sept.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Medical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String petName;
    private int petID;
    private int petAge;
    private String petSex;
    private int petWeight;
    private String petType;
    private String petBreed;

    //Getters
    public String getEmail() {
        return email;
    }

    public String getPetName() {
        return petName;
    }

    public int getPetID() {
        return petID;
    }

    public int getPetAge() {
        return petAge;
    }

    public String getPetSex() {
        return petSex;
    }

    public int getPetWeight() {
        return petWeight;
    }

    public String getPetType() {
        return petType;
    }

    public String getPetBreed() {
        return petBreed;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public void setPetWeight(int petWeight) {
        this.petWeight = petWeight;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }
   
}
