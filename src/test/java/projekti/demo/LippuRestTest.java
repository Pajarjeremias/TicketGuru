package projekti.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "yllapitaja", authorities = {"Yllapitaja"})

public class LippuRestTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLippuPost() throws Exception {
        String uusiLippuJson = """
        {
            "myynti_id": 1,
            "tapahtuman_lipputyypit_id": 1
        }
        """;

        mockMvc.perform(post("/api/liput")
                .contentType(MediaType.APPLICATION_JSON)
                .content(uusiLippuJson))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.koodi").exists());
    }
    
}
