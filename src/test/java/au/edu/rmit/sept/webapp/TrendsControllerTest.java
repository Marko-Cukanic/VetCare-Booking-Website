package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.TrendsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(TrendsController.class)
public class TrendsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test sorting trends by date (checking HTML content related to the date)
    @Test
    public void shouldContainTrendDates_Success() throws Exception {
        mockMvc.perform(get("/trends").param("filter", "date"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("September 6, 2024")))
                .andExpect(content().string(containsString("April 6, 2024")))
                .andExpect(content().string(containsString("September 4, 2024")));
    }

    // Test sorting trends by relevance (checking HTML content related to relevance)
    @Test
    public void shouldContainTrendRelevance_Success() throws Exception {
        mockMvc.perform(get("/trends").param("filter", "relevance"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Scholarship Programs")))
                .andExpect(content().string(containsString("2024 Pet Industry Trends")))
                .andExpect(content().string(containsString("Collecting Deposits for Appointments")));
    }
}
