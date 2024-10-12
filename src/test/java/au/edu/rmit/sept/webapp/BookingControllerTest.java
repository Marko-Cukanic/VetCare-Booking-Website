package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.BookingController;
import au.edu.rmit.sept.webapp.service.BookingService;
import au.edu.rmit.sept.webapp.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private Model model;

    @Mock
    private EmailService emailService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Positive Test Case: Test clinic selector page loads successfully
    @Test
    void testSelectClinicPageLoad() {
        String viewName = bookingController.selectClinic();
        assertEquals("clinicSelector", viewName, "The clinic selector page should load successfully.");
    }

    // Positive Test Case: Test make booking page loads successfully with valid clinic name
    @Test
    void testMakeBookingPageLoadWithValidClinic() {
        String clinicName = "Sunshine Vet Clinic";
        String viewName = bookingController.makeBooking(clinicName, model);
        verify(model).addAttribute("clinicName", clinicName);
        assertEquals("makeBooking", viewName, "The make booking page should load successfully with a valid clinic name.");
    }

    // Negative Test Case: Test make booking page with an empty clinic name
    @Test
    void testMakeBookingPageLoadWithEmptyClinic() {
        String clinicName = "";
        String viewName = bookingController.makeBooking(clinicName, model);
        verify(model).addAttribute("clinicName", clinicName);
        assertEquals("makeBooking", viewName, "The make booking page should still load even with an empty clinic name.");
    }

    // Positive Test Case: Test create booking with available time slot
    @Test
    void testCreateBookingWithAvailableTimeSlot() {
        LocalDate bookingDate = LocalDate.now().plusDays(1);
        String timeSlot = "10:00";
        String clinicName = "Sunshine Vet Clinic";
        String userEmail = "testuser@example.com";

        when(bookingService.isTimeSlotAvailable(any(LocalDate.class), anyString(), anyString())).thenReturn(true);

        String viewName = bookingController.createBooking(bookingDate, timeSlot, clinicName, userEmail, redirectAttributes);
        verify(bookingService).createBooking(bookingDate, timeSlot, clinicName, userEmail);
        verify(emailService).sendBookingConfirmationEmail(userEmail, clinicName, bookingDate, timeSlot); // Verify email is sent
        assertEquals("redirect:/bookings", viewName, "Booking should be created successfully when the time slot is available.");
    }

// Negative Test Case: Test create booking with an already booked time slot
@Test
void testCreateBookingWithUnavailableTimeSlot() {
    LocalDate bookingDate = LocalDate.now().plusDays(1);
    String timeSlot = "10:00";
    String clinicName = "Sunshine Vet Clinic";
    String userEmail = "testuser@example.com";

    when(bookingService.isTimeSlotAvailable(any(LocalDate.class), anyString(), anyString())).thenReturn(false);

    String viewName = bookingController.createBooking(bookingDate, timeSlot, clinicName, userEmail, redirectAttributes);
    verify(bookingService, never()).createBooking(any(LocalDate.class), anyString(), anyString(), anyString());
    assertEquals("redirect:/makebooking", viewName, "Booking should fail when the time slot is already booked.");
}


    // Positive Test Case: Test fetching available time slots for a date
    @Test
    void testGetAvailableTimeSlots() {
        LocalDate bookingDate = LocalDate.now().plusDays(1);
        String clinicName = "Sunshine Vet Clinic";
        List<String> expectedTimeSlots = List.of("10:00", "10:20", "11:00");

        when(bookingService.getAvailableTimeSlots(any(LocalDate.class), anyString())).thenReturn(expectedTimeSlots);

        List<String> availableTimeSlots = bookingController.getAvailableTimeSlots(bookingDate, clinicName);
        assertEquals(expectedTimeSlots, availableTimeSlots, "The available time slots should be correctly returned for the given date and clinic.");
    }

    // Positive Test Case: Test cancel booking with a valid booking ID
    @Test
    void testCancelBookingWithValidId() {
        Long bookingId = 1L;

        String viewName = bookingController.cancelBooking(bookingId, redirectAttributes);
        verify(bookingService).deleteBooking(bookingId);
        assertEquals("redirect:/bookings", viewName, "Booking should be canceled successfully with a valid booking ID.");
    }
}
