package au.edu.rmit.sept.webapp.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "time_slot", nullable = false)
    private String timeSlot;

    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    // Constructors, getters, and setters

    public Booking() {
        this.createdAt = java.time.LocalDateTime.now();
    }

    public Booking(LocalDate bookingDate, String timeSlot) {
        this.bookingDate = bookingDate;
        this.timeSlot = timeSlot;
        this.createdAt = java.time.LocalDateTime.now();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
