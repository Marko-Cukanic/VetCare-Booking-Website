package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.EducationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(EducationController.class)
@AutoConfigureMockMvc
public class EducationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Unit Test: Checks if the controller returns the correct view name (Positive Test)
    @Test
    public void shouldReturnEducationView_Success() throws Exception {
        mockMvc.perform(get("/resources"))
                .andExpect(status().isOk())
                .andExpect(view().name("education")); // Unit test checks only the view name
    }

    // Integration Test: Validate that the view contains the search bar and filter elements
    @Test
    public void shouldContainSearchAndFilterElementsInEducationPage() throws Exception {
        mockMvc.perform(get("/resources"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Search Articles...")))
                .andExpect(content().string(containsString("Filter by Topic:")))
                .andExpect(content().string(containsString("Filter by Date:"))); // Integration test validates view content
    }

    // System Test: Validate search functionality with a valid input (Positive Test)
    @Test
    public void shouldReturnCorrectArticlesForValidSearchInput() throws Exception {
        ResultActions result = mockMvc.perform(get("/resources").param("search", "Dog"));
        result.andExpect(status().isOk())
              .andExpect(content().string(containsString("Dog Behavior Problems General Care"))); // System test checks interaction of all components
    }

    // Negative Test: Validate search functionality with an invalid input (Special characters)
    @Test
    public void shouldHandleInvalidSearchInputGracefully() throws Exception {
        mockMvc.perform(get("/resources").param("search", "<script>alert('test')</script>"))
                .andExpect(status().isOk())  // Ensure the page still loads correctly
                .andExpect(content().string(containsString("Explore our collection of articles on pet care and veterinary medicine.")));  // Validate no XSS or injection attack worked
    }

    // Boundary Test: Validate search functionality with empty input (Positive Test)
    @Test
    public void shouldReturnAllArticlesWhenSearchInputIsEmpty() throws Exception {
        ResultActions result = mockMvc.perform(get("/resources").param("search", ""));
        result.andExpect(status().isOk())
              .andExpect(content().string(containsString("Explore our collection of articles on pet care and veterinary medicine.")));
    }

    // System Test: Validate filter functionality with valid date range (Positive Test)
    @Test
    public void shouldReturnArticlesForValidDateRange() throws Exception {
        ResultActions result = mockMvc.perform(get("/resources").param("date", "2017-2020"));
        result.andExpect(status().isOk())
              .andExpect(content().string(containsString("Functional Foods in Pet Nutrition")));
    }

    // Negative Test: Validate filter functionality with an invalid date range
    @Test
    public void shouldHandleInvalidDateRangeGracefully() throws Exception {
        mockMvc.perform(get("/resources").param("date", "invalid-date"))
                .andExpect(status().isOk()) // Ensure page loads correctly
                .andExpect(content().string(containsString("Explore our collection of articles on pet care and veterinary medicine."))); // Default message or all articles shown
    }

    // Consolidated End-to-End Test: Validate combined filtering for the Education Page
    @Test
    public void endToEndTest_shouldFilterArticlesByTopicAndDateSuccessfully() throws Exception {
        mockMvc.perform(get("/resources")
                .param("topic", "Exercise")
                .param("date", "2017-2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pet Care Exercises"))) // Expected article for topic "Exercise" and date range "2017-2020"
                .andExpect(content().string(containsString("Teach Your Dog to Trade Items Exercises"))); // Multiple results expected for topic and date combination
    }

    // Consolidated Acceptance Test: Validate that the page displays the correct header and navigation elements
    @Test
    public void acceptanceTest_shouldDisplayCorrectHeaderAndNavigationElements() throws Exception {
        mockMvc.perform(get("/resources"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Educational Resources"))) // Header validation
                .andExpect(content().string(containsString("Make a Booking"))) // Navigation link validation
                .andExpect(content().string(containsString("My Booking/s")))
                .andExpect(content().string(containsString("Prescription Management")))
                .andExpect(content().string(containsString("Medical History")));
    }
}
