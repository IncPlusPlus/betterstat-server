package io.github.incplusplus.betterstat.web.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.HttpStatus.OK;

import io.github.incplusplus.betterstat.Application;
import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.persistence.model.Day.SetPointTimeTuple;
import io.github.incplusplus.betterstat.testhelpers.WithMockUser;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class DayControllerIT {
  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void initialiseRestAssuredMockMvcWebApplicationContext() {
    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
  }

  @Test
  @WithMockUser
  public void whenPostRequestToDaysAndValidDay_thenCorrectResponse() {
    Day day =
        new Day(
            "5efbe34401854e6f0bde6c4f",
            "Day 100",
            new TreeSet<>(
                Set.of(
                    new SetPointTimeTuple(BigDecimal.valueOf(25), LocalTime.of(14, 30, 45, 829)))));

    Day response =
        given()
            .contentType(ContentType.JSON)
            .body(day)
            .when()
            .post("/day")
            .then()
            .log()
            .ifValidationFails()
            .statusCode(OK.value())
            .extract()
            .body()
            .as(Day.class);

    // The ID should be different as a new Day was created (the id value we provide is ignored)
    assertNotEquals(day.getId(), response.getId());
    // Change the one thing that's different so we can do a sweeping .equals() comparison
    response.setId(day.getId());
    assertEquals(day, response);
  }
}
