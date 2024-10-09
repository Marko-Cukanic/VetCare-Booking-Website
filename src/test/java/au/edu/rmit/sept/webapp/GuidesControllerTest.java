package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.GuidesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.*;

@WebMvcTest(GuidesController.class)
public class GuidesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Unit Test: Checks whether the controller returns the correct view name
    @Test
    public void unitTest_shouldReturnGuidesView_Success() throws Exception {
        mockMvc.perform(get("/guides"))
                .andExpect(status().isOk())
                .andExpect(view().name("guides")); // Checks that the controller returns the correct view
    }

    // Integration Test: Validate that the view contains search bar and filter elements
    @Test
    public void integrationTest_shouldContainSearchAndFilterElementsInGuidesPage() throws Exception {
        mockMvc.perform(get("/guides"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Search Guides...")))
                .andExpect(content().string(containsString("Filter by Topic:")))
                .andExpect(content().string(containsString("Filter by Estimated Reading Time:"))); // Integration test validates view content
    }

    // Consolidated System Test: Validate filtering guides by topic or reading time
    @Test
    public void systemTest_shouldFilterGuidesByTopicOrReadingTime() throws Exception {
        // Filter by topic
        mockMvc.perform(get("/guides").param("topic", "Nutrition/Diet"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Importance of Good Nutrition"))); // Expected guide for the topic "Nutrition/Diet"

        // Filter by reading time
        mockMvc.perform(get("/guides").param("time", "5-10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pet Breeds Guide"))); // Expected guide for reading time "5-10"
    }

    // Consolidated End-to-End Test: Validate filtering guides by both topic and estimated reading time
    @Test
    public void endToEndTest_shouldFilterGuidesByTopicAndReadingTime() throws Exception {
        mockMvc.perform(get("/guides")
                .param("topic", "General Care")
                .param("time", "5-10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pet Responsibilities Guide"))); // Expected guide for "General Care" with reading time "5-10"
    }

    // Acceptance Test: Validate that the guides page displays the correct header and elements
    @Test
    public void acceptanceTest_shouldContainHeaderAndContentInGuidesPage() throws Exception {
        mockMvc.perform(get("/guides"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Access detailed guides on various topics related to pet care."))) // Header validation
                .andExpect(content().string(containsString("Importance of Good Nutrition"))) // Guide title validation
                .andExpect(content().string(containsString("Pet Breeds Guide"))); // Another guide title validation
    }

    // Positive Test: Validate the controller returns guides for a valid topic
    @Test
    public void positiveTest_shouldReturnGuidesForValidTopic() throws Exception {
        mockMvc.perform(get("/guides").param("topic", "Pet Breeds"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pet Breeds Guide"))); // Expected guide for the topic "Pet Breeds"
    }

    // Negative Test: Validate the controller handles invalid topic gracefully
    @Test
    public void negativeTest_shouldHandleInvalidTopicGracefully() throws Exception {
        mockMvc.perform(get("/guides").param("topic", "InvalidTopic"))
                .andExpect(status().isOk())  // Ensure the page still loads correctly
                .andExpect(content().string(containsString("Access detailed guides on various topics related to pet care."))); // No specific guide shown
    }

    // Consolidated Boundary Test: Validate filtering guides with minimum and maximum estimated reading time
    @Test
    public void boundaryTest_shouldReturnGuidesForReadingTimeBoundaries() throws Exception {
        // Minimum estimated reading time (5 minutes)
        mockMvc.perform(get("/guides").param("time", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pet Breeds Guide"))); // Guide with estimated reading time of 5 minutes

        // Maximum estimated reading time (15 minutes)
        mockMvc.perform(get("/guides").param("time", "15"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Things You Learn from Your Pet"))); // Guide with estimated reading time of 15 minutes

        // Exceeding estimated reading time (e.g., 25 minutes)
        mockMvc.perform(get("/guides").param("time", "25"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Access detailed guides on various topics related to pet care."))); // No specific guide shown
    }
}
