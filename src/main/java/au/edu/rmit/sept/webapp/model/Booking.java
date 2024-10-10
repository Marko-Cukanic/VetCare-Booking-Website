package au.edu.rmit.sept.webapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")  // Ensure it maps to the 'bookings' table in MySQL
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "time_slot", nullable = false)
    private String timeSlot;

    @Column(name = "clinic_name", nullable = false) // New column for storing the clinic name
    private String clinicName;

    @Column(name = "doctor", nullable = false)
    private String doctor;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors, getters, and setters

    public Booking() {
        this.createdAt = LocalDateTime.now();
    }

    public Booking(LocalDate bookingDate, String timeSlot, String clinicName, String doctor, String userEmail) {
        this.bookingDate = bookingDate;
        this.timeSlot = timeSlot;
        this.clinicName = clinicName;
        this.doctor = doctor;
        this.userEmail = userEmail;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getDoctor(){
        return doctor;
    }

    public void setDoctor(String doctor){
        this.doctor = doctor;
    }

    // Getter and Setter for userEmail
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
