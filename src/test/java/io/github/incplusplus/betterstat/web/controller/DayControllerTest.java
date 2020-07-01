package io.github.incplusplus.betterstat.web.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.persistence.model.Day.SetPointTimeTuple;
import io.github.incplusplus.betterstat.service.DayService;
import io.github.incplusplus.betterstat.testhelpers.MockMvcUtils;
import io.github.incplusplus.betterstat.web.exception.ExceptionsHandler;
import io.github.incplusplus.betterstat.web.mappers.DayMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class DayControllerTest {
  @Mock private DayService dayService;
  /**
   * A Mapper that exists for the sole purpose of being used inside the RestController. See <a
   * href="https://github.com/mapstruct/mapstruct/issues/1261#issuecomment-400650385">mapstruct/mapstruct#1261</a>
   */
  @SuppressWarnings({"unused", "WrongUsageOfMappersFactory"})
  @Spy
  private final DayMapper mapper = Mappers.getMapper(DayMapper.class);

  @InjectMocks private DayController dayController;
  @InjectMocks private ExceptionsHandler exceptionsHandler;
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

  @Before
  public void initialiseRestAssuredMockMvcStandalone() {
    RestAssuredMockMvc.standaloneSetup(dayController, exceptionsHandler);
  }

  @Test
  public void givenNoExistingDaysWhenGetDaysThenRespondWithStatusOkAndEmptyArray() {
    when(dayService.findAll()).thenReturn(Collections.emptyList());

    given()
        .config(MockMvcUtils.noSecurity())
        .when()
        .get("/day")
        .then()
        .log()
        .ifValidationFails()
        .statusCode(OK.value())
        .body(is(equalTo("[]")));
  }

  @Test
  public void whenPostRequestToDaysAndValidDay_thenCorrectResponse() {
    when(dayService.createDay(any())).thenReturn(day1);

    Day response =
        given()
            .config(MockMvcUtils.noSecurity())
            .contentType(ContentType.JSON)
            .body(day1)
            .when()
            .post("/day")
            .then()
            .log()
            .ifValidationFails()
            .statusCode(OK.value())
            .body("id", equalTo(day1.getId()))
            .body("name", equalTo(day1.getName()))
            .body("times[0].setPoint", equalTo(20))
            .body("times[0].time[0]", equalTo(14))
            .body("times[0].time[1]", equalTo(30))
            .extract()
            .body()
            .as(Day.class);

    assertEquals(day1, response);
  }

  @Test
  public void whenPostRequestToDaysAndInvalidDay_thenIncorrectResponse() {
    given()
        .config(MockMvcUtils.noSecurity())
        .contentType(ContentType.JSON)
        .body(badDay1Json)
        .when()
        .post("/day")
        .then()
        .log()
        .ifValidationFails()
        .statusCode(BAD_REQUEST.value());
  }

  @Test
  public void whenGetRequestToDaysAndDaysAreStored_thenDaysReturned() {
    when(dayService.findAll()).thenReturn(List.of(day1, day2));

    List<Day> response =
        given()
            .config(MockMvcUtils.noSecurity())
            .contentType(ContentType.JSON)
            .body(day1)
            .when()
            .get("/day")
            .then()
            .log()
            .ifValidationFails()
            .statusCode(OK.value())
            .extract()
            // Big ol' thank you to this fella https://stackoverflow.com/a/39588918/1687436
            .body()
            .jsonPath()
            .getList(".", Day.class);

    assertEquals(List.of(day1, day2), response);
  }
}
