package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controller.EducationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(EducationController.class)
public class EducationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test filtering by topic
    @Test
    public void shouldFilterArticlesByTopic() throws Exception {
        mockMvc.perform(get("/resources").param("topic", "Exercise"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].topic", everyItem(is("Exercise"))));
    }

    // Test filtering by date range
    @Test
    public void shouldFilterArticlesByDateRange() throws Exception {
        mockMvc.perform(get("/resources").param("date", "2017-2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].date", everyItem(greaterThanOrEqualTo("2017-01-01"))))
                .andExpect(jsonPath("$[*].date", everyItem(lessThanOrEqualTo("2020-12-31"))));
    }
}
