package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.MyBookingsController;
import au.edu.rmit.sept.webapp.model.Booking;
import au.edu.rmit.sept.webapp.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(MyBookingsController.class)
public class MyBookingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    public void getBookings_DisplayAllBookings_Success() throws Exception {
        // Mock a list of bookings
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        Mockito.when(bookingService.getAllBookings()).thenReturn(Arrays.asList(booking1, booking2));

        // Perform GET request to fetch bookings
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bookings"));
    }
}
