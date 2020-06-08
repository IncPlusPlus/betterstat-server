package io.github.incplusplus.betterstat.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DayControllerIT {
  @Autowired private MockMvc mockMvc;

  @Test
  public void whenPostRequestToDaysAndValidDay_thenCorrectResponse() throws Exception {
    String day =
        "{\"id\":\"5ede83167a72107d93611c2f\",\"name\":\"sample schedule\",\"times\":[{\"setPoint\":0,\"time\":\"14:43:45.829\"}]}";

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/day")
                .content(day)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }
}
