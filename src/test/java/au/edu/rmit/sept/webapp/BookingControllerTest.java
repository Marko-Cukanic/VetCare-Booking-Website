package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.BookingController;
import au.edu.rmit.sept.webapp.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    public void createBooking_ValidData_Success() throws Exception {
        LocalDate bookingDate = LocalDate.now();
        String timeSlot = "10:00 AM";
        String clinicName = "Sunshine Vet Clinic";

        // Mock the service to indicate the time slot is available
        Mockito.when(bookingService.isTimeSlotAvailable(bookingDate, timeSlot, clinicName)).thenReturn(true);

        // Perform POST request to create a booking
        mockMvc.perform(post("/createBooking")
                .param("bookingDate", bookingDate.toString())
                .param("timeSlot", timeSlot)
                .param("clinicName", clinicName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }

    @Test
    public void cancelBooking_ValidBookingId_Success() throws Exception {
        Long bookingId = 1L;

        // Mock the service call to delete the booking
        Mockito.doNothing().when(bookingService).deleteBooking(bookingId);

        // Perform GET request to cancel the booking
        mockMvc.perform(get("/cancelBooking/" + bookingId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }

    @Test
    public void rescheduleBooking_ValidBookingId_Success() throws Exception {
        Long bookingId = 1L;

        // Mock the service call to delete the booking
        Mockito.doNothing().when(bookingService).deleteBookingById(bookingId);

        // Perform GET request to reschedule the booking
        mockMvc.perform(get("/rescheduleBooking/" + bookingId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/makebooking"));
    }
}
