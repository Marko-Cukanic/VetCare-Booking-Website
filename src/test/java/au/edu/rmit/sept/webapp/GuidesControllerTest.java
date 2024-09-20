package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.GuidesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(GuidesController.class)
public class GuidesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test filtering by topic
    @Test
    public void shouldFilterGuidesByTopic() throws Exception {
        mockMvc.perform(get("/guides").param("topic", "Nutrition/Diet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].topic", everyItem(is("Nutrition/Diet"))));
    }

    // Test filtering by estimated reading time
    @Test
    public void shouldFilterGuidesByReadingTime() throws Exception {
        mockMvc.perform(get("/guides").param("time", "5-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].time", everyItem(allOf(greaterThanOrEqualTo(5), lessThanOrEqualTo(10)))));
    }
}
