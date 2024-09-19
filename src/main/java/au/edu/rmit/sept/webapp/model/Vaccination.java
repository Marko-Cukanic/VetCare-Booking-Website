package au.edu.rmit.sept.webapp.model;

import java.util.stream.Stream;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String petName;

    private int distemper;
    private int canineParvovirus;
    private int bordetella;
    private int lymeDisease;
    private int rabies;

    private int panleukopenia;
    private int caliciVirus;
    private int immunodeficiencyVirus;
    private int chlamydiaFelis;
    private int leukemiaVirus;

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPetName() {
        return petName;
    }

    public boolean isDistemper() {
        return distemper == 1;
    }

    public boolean isCanineParvovirus() {
        return canineParvovirus == 1;
    }

    public boolean isBordetella() {
        return bordetella == 1;
    }

    public boolean isLymeDisease() {
        return lymeDisease == 1;
    }

    public boolean isRabies() {
        return rabies == 1;
    }

    public boolean isPanleukopenia() {
        return panleukopenia == 1;
    }

    public boolean isCaliciVirus() {
        return caliciVirus == 1;
    }

    public boolean isImmunodeficiencyVirus() {
        return immunodeficiencyVirus == 1;
    }

    public boolean isChlamydiaFelis() {
        return chlamydiaFelis == 1;
    }

    public boolean isLeukemiaVirus() {
        return leukemiaVirus == 1;
    }

    // Setters 
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setDistemper(boolean distemper) {
        this.distemper = distemper ? 1 : 0;
    }

    public void setCanineParvovirus(boolean canineParvovirus) {
        this.canineParvovirus = canineParvovirus ? 1 : 0;
    }

    public void setBordetella(boolean bordetella) {
        this.bordetella = bordetella ? 1 : 0;
    }

    public void setLymeDisease(boolean lymeDisease) {
        this.lymeDisease = lymeDisease ? 1 : 0;
    }

    public void setRabies(boolean rabies) {
        this.rabies = rabies ? 1 : 0;
    }

    public void setPanleukopenia(boolean panleukopenia) {
        this.panleukopenia = panleukopenia ? 1 : 0;
    }

    public void setCaliciVirus(boolean caliciVirus) {
        this.caliciVirus = caliciVirus ? 1 : 0;
    }

    public void setImmunodeficiencyVirus(boolean immunodeficiencyVirus) {
        this.immunodeficiencyVirus = immunodeficiencyVirus ? 1 : 0;
    }

    public void setChlamydiaFelis(boolean chlamydiaFelis) {
        this.chlamydiaFelis = chlamydiaFelis ? 1 : 0;
    }

    public void setLeukemiaVirus(boolean leukemiaVirus) {
        this.leukemiaVirus = leukemiaVirus ? 1 : 0;
    }

    public Stream<Medical> stream() {
        throw new UnsupportedOperationException("Unimplemented method 'stream'");
    }
}
