package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.VideosController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.*;

@WebMvcTest(VideosController.class)
public class VideosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Unit Test: Validate the controller returns the correct view name
    @Test
    public void unitTest_shouldReturnVideosView_Success() throws Exception {
        mockMvc.perform(get("/videos"))
                .andExpect(status().isOk())
                .andExpect(view().name("videos")); // Checks that the controller returns the correct view
    }

    // Integration Test: Checks if the view contains search bar and filter elements
    @Test
    public void integrationTest_shouldContainSearchAndFilterElementsInVideosPage() throws Exception {
        mockMvc.perform(get("/videos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Search Videos...")))
                .andExpect(content().string(containsString("Filter by Topic:")))
                .andExpect(content().string(containsString("Filter by Duration:"))); // Integration test validates view content
    }

    // Consolidated System Test: Validate filtering by topic or duration
    @Test
    public void systemTest_shouldFilterVideosByTopicOrDuration() throws Exception {
        // Filter by topic
        mockMvc.perform(get("/videos").param("topic", "Training"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Basic Training - Cats"))) // Expected video for the topic "Training"
                .andExpect(content().string(containsString("Dog Training Basics"))); // Multiple videos expected for the topic

        // Filter by duration
        mockMvc.perform(get("/videos").param("duration", "0-5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pet Care Grooming"))) // Expected video with duration "0-5"
                .andExpect(content().string(containsString("Basic Training - Cats"))); // Multiple videos expected for duration "0-5"
    }

    // Consolidated End-to-End Test: Validate filtering videos by both topic and duration
    @Test
    public void endToEndTest_shouldFilterVideosByTopicAndDuration() throws Exception {
        mockMvc.perform(get("/videos")
                .param("topic", "Training")
                .param("duration", "0-5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Basic Training - Cats"))) // Expected video for topic "Training" and duration "0-5"
                .andExpect(content().string(containsString("Pet Care Grooming"))); // Multiple results expected for topic and duration combination
    }

    // Acceptance Test: Validate that the videos page displays the correct header and elements
    @Test
    public void acceptanceTest_shouldContainHeaderAndContentInVideosPage() throws Exception {
        mockMvc.perform(get("/videos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Watch educational videos to learn more about taking care of your pets."))) // Header validation
                .andExpect(content().string(containsString("Pet Care Grooming"))) // Video title validation
                .andExpect(content().string(containsString("Treating Health Issues"))); // Another video title validation
    }

    // Positive Test: Validate the controller returns videos for a valid topic
    @Test
    public void positiveTest_shouldReturnVideosForValidTopic() throws Exception {
        mockMvc.perform(get("/videos").param("topic", "Health"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Treating Health Issues"))); // Expected video for the topic "Health"
    }

    // Negative Test: Validate the controller handles invalid topic gracefully
    @Test
    public void negativeTest_shouldHandleInvalidTopicGracefully() throws Exception {
        mockMvc.perform(get("/videos").param("topic", "InvalidTopic"))
                .andExpect(status().isOk())  // Ensure the page still loads correctly
                .andExpect(content().string(containsString("Watch educational videos to learn more about taking care of your pets."))); // Default message or all videos shown
    }

    // Consolidated Boundary Test: Validate filtering videos with minimum, maximum, and exceeding duration values
    @Test
    public void boundaryTest_shouldReturnVideosForDurationBoundaries() throws Exception {
        // Minimum duration value (0 minutes)
        mockMvc.perform(get("/videos").param("duration", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pet Care Grooming"))); // Video with duration of 0 minutes (minimum)

        // Maximum duration value (5 minutes)
        mockMvc.perform(get("/videos").param("duration", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Dog Training Basics"))); // Video with duration of 5 minutes (maximum)

        // Exceeding duration value (e.g., 20 minutes)
        mockMvc.perform(get("/videos").param("duration", "20"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Watch educational videos to learn more about taking care of your pets."))); // No specific video shown
    }
}
