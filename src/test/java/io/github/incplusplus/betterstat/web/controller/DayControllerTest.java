package io.github.incplusplus.betterstat.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.persistence.model.Day.SetPointTimeTuple;
import io.github.incplusplus.betterstat.service.DayService;
import io.github.incplusplus.betterstat.web.mappers.DayMapperImpl;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest({DayController.class, DayMapperImpl.class})
public class DayControllerTest {
  private static final Day day1 =
      new Day(
          "5ede83167a72107d93611c2f",
          "Day 1",
          new TreeSet<>(
              Set.of(new SetPointTimeTuple(BigDecimal.valueOf(20), LocalTime.of(14, 30)))));
  private static final Day day2 =
      new Day(
          "5ede83167a78312d93611c2f",
          "Day 2",
          new TreeSet<>(
              Set.of(
                  new SetPointTimeTuple(BigDecimal.valueOf(25), LocalTime.of(8, 0)),
                  new SetPointTimeTuple(BigDecimal.valueOf(30), LocalTime.of(16, 30)))));
  private static final String day1Json =
      "{\"id\":\"5ede83167a72107d93611c2f\",\"name\":\"Day 1\",\"times\":[{\"setPoint\":20,\"time\":\"14:30:00\"}]}";
  private static final String badDay1Json =
      "{\"id\":\"5ede83167a72107d93611c2f\",\"times\":[{\"setPoint\":20,\"time\":\"14:30:00\"}]}";
  private static final String day2Json =
      "{\"id\":\"5ede83167a78312d93611c2f\",\"name\":\"Day 2\",\"times\":[{\"setPoint\":25,\"time\":\"08:00:00\"},{\"setPoint\":30,\"time\":\"16:30:00\"}]}";
  @Autowired private MockMvc mockMvc;
  @MockBean private DayService dayService;

  @Test
  public void whenPostRequestToDaysAndValidDay_thenCorrectResponse() throws Exception {
    when(dayService.createDay(any())).thenReturn(day1);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/day")
                .content(day1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void whenPostRequestToDaysAndInvalidDay_thenCorrectResponse() throws Exception {
    when(dayService.createDay(any())).thenReturn(day1);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/day")
                .content(badDay1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void whenGetRequestToDaysAndDaysAreStored_thenDaysReturned() throws Exception {
    when(dayService.findAll()).thenReturn(List.of(day1, day2));
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/day")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json("[" + day1Json + "," + day2Json + "]"));
  }
}
