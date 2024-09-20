package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.VideosController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(VideosController.class)
public class VideosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test filtering by topic
    @Test
    public void shouldFilterVideosByTopic() throws Exception {
        mockMvc.perform(get("/videos").param("topic", "Training"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].topic", everyItem(is("Training"))));
    }

    // Test filtering by duration
    @Test
    public void shouldFilterVideosByDuration() throws Exception {
        mockMvc.perform(get("/videos").param("duration", "0-5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].duration", everyItem(allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(5)))));
    }
}
