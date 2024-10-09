package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.TrendsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TrendsController.class)
public class TrendsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Unit Test: Validate the controller returns the correct view name
    @Test
    public void unitTest_shouldReturnTrendsView_Success() throws Exception {
        mockMvc.perform(get("/trends"))
                .andExpect(status().isOk())
                .andExpect(view().name("trends")); // Checks that the controller returns the correct view
    }

    // Integration Test: Checks if the view contains the search bar and sorting options
    @Test
    public void integrationTest_shouldContainSearchAndSortingElementsInTrendsPage() throws Exception {
        mockMvc.perform(get("/trends"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Search Trends...")))
                .andExpect(content().string(containsString("Sort by:")))
                .andExpect(content().string(containsString("Publication Date")))
                .andExpect(content().string(containsString("Relevance"))); // Integration test validates view content
    }

    // Consolidated System Test: Validate sorting trends by date or relevance
    @Test
    public void systemTest_shouldSortTrendsByDateOrRelevance() throws Exception {
        // Validate sorting by date
        mockMvc.perform(get("/trends").param("filter", "date"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("September 6, 2024"))) // Expected trend date
                .andExpect(content().string(containsString("April 6, 2024"))) // Another expected trend date
                .andExpect(content().string(containsString("September 4, 2024"))); // Multiple trends expected for date filter

        // Validate sorting by relevance
        mockMvc.perform(get("/trends").param("filter", "relevance"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Scholarship Programs"))) // Expected trend by relevance
                .andExpect(content().string(containsString("2024 Pet Industry Trends"))) // Multiple trends expected for relevance filter
                .andExpect(content().string(containsString("Collecting Deposits for Appointments")));
    }

    // Consolidated End-to-End Test: Validate sorting and filtering trends by both date and relevance
    @Test
    public void endToEndTest_shouldFilterAndSortTrendsByDateAndRelevance() throws Exception {
        mockMvc.perform(get("/trends")
                .param("filter", "date")
                .param("filter", "relevance"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Scholarship Programs"))) // Expected trend for date and relevance
                .andExpect(content().string(containsString("2024 Pet Industry Trends"))); // Multiple trends expected for combined filter
    }

    // Acceptance Test: Validate that the trends page displays the correct header and content
    @Test
    public void acceptanceTest_shouldContainHeaderAndContentInTrendsPage() throws Exception {
        mockMvc.perform(get("/trends"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Stay updated with the latest trends in pet care and veterinary science."))) // Header validation
                .andExpect(content().string(containsString("New Scholarship Programs"))) // Trend title validation
                .andExpect(content().string(containsString("2024 Pet Industry Trends"))); // Another trend title validation
    }

    // Positive Test: Validate the controller returns trends for a valid filter (date)
    @Test
    public void positiveTest_shouldReturnTrendsForValidDateFilter() throws Exception {
        mockMvc.perform(get("/trends").param("filter", "date"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("September 6, 2024"))); // Expected trend for date filter
    }

    // Negative Test: Validate the controller handles invalid filter gracefully
    @Test
    public void negativeTest_shouldHandleInvalidFilterGracefully() throws Exception {
        mockMvc.perform(get("/trends").param("filter", "invalidFilter"))
                .andExpect(status().isOk())  // Ensure the page still loads correctly
                .andExpect(content().string(containsString("Stay updated with the latest trends in pet care and veterinary science."))); // Default message or all trends shown
    }

    // Consolidated Boundary Test: Validate sorting trends with minimum, maximum, and exceeding relevance values
    @Test
    public void boundaryTest_shouldReturnTrendsForRelevanceBoundaries() throws Exception {
        // Minimum relevance value
        mockMvc.perform(get("/trends").param("relevance", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Collecting Deposits for Appointments"))); // Trend with minimum relevance value

        // Maximum relevance value
        mockMvc.perform(get("/trends").param("relevance", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Scholarship Programs"))); // Trend with maximum relevance value

        // Exceeding relevance value (e.g., 10)
        mockMvc.perform(get("/trends").param("relevance", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Stay updated with the latest trends in pet care and veterinary science."))); // No specific trend shown
    }
}
