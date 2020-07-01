package io.github.incplusplus.betterstat.web.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.persistence.model.Day.SetPointTimeTuple;
import io.github.incplusplus.betterstat.web.mappers.DayMapper;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * Tests the {@link DayMapper} methods to ensure that no data is lost when converting between the
 * domain object and the DTO.
 */
public class DayDtoTest {
  private final DayMapper mapper = Mappers.getMapper(DayMapper.class);
  private final Day day =
      new Day(
          "5efbe33961854e6f0bdo9c4f",
          "I've seen a lotta days and boy, this sure is one.",
          new TreeSet<>(
              Set.of(new SetPointTimeTuple(BigDecimal.valueOf(15), LocalTime.of(6, 30, 0)))));
  private final Day day2 =
      new Day(
          "6efbe33961859e6d0edo9c4f",
          "Day 2, electric boogaloo.",
          new TreeSet<>(
              Set.of(new SetPointTimeTuple(BigDecimal.valueOf(8), LocalTime.of(16, 45)))));
  private final DayDto dayDto =
      new DayDto(
          "5efbe33961854e6f0bdo9c4f",
          "I've seen a lotta days and boy, this sure is one.",
          new TreeSet<>(
              Set.of(
                  new DayDto.SetPointTimeTuple(BigDecimal.valueOf(15), LocalTime.of(6, 30, 0)))));
  private final DayDto dayDto2 =
      new DayDto(
          "6efbe33961859e6d0edo9c4f",
          "Day 2, electric boogaloo.",
          new TreeSet<>(
              Set.of(new DayDto.SetPointTimeTuple(BigDecimal.valueOf(8), LocalTime.of(16, 45)))));

  @Test
  public void testFromDto() {
    Day converted = mapper.fromDto(dayDto);
    assertEquals(day, converted);
  }

  @Test
  public void testToDto() {
    DayDto converted = mapper.toDTO(day);
    assertEquals(dayDto, converted);
  }

  @Test
  public void testMapDaysToDayDto() {
    List<DayDto> converted = mapper.mapDaysToDayDto(List.of(day, day2));
    assertEquals(List.of(dayDto, dayDto2), converted);
  }
}
