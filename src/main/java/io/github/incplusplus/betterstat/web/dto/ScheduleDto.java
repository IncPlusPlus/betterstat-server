package io.github.incplusplus.betterstat.web.dto;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.incplusplus.betterstat.persistence.model.Day;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ScheduleDto {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private String id;

  @Valid @NotNull private String name;
  @Valid @NotNull private Day sunday;
  @Valid @NotNull private Day monday;
  @Valid @NotNull private Day tuesday;
  @Valid @NotNull private Day wednesday;
  @Valid @NotNull private Day thursday;
  @Valid @NotNull private Day friday;
  @Valid @NotNull private Day saturday;

  @SuppressWarnings("unused")
  public ScheduleDto() {}

  public ScheduleDto(
      String id,
      String name,
      Day sunday,
      Day monday,
      Day tuesday,
      Day wednesday,
      Day thursday,
      Day friday,
      Day saturday) {
    this.id = id;
    this.name = name;
    this.sunday = sunday;
    this.monday = monday;
    this.tuesday = tuesday;
    this.wednesday = wednesday;
    this.thursday = thursday;
    this.friday = friday;
    this.saturday = saturday;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Day getSunday() {
    return sunday;
  }

  public void setSunday(Day sunday) {
    this.sunday = sunday;
  }

  public Day getMonday() {
    return monday;
  }

  public void setMonday(Day monday) {
    this.monday = monday;
  }

  public Day getTuesday() {
    return tuesday;
  }

  public void setTuesday(Day tuesday) {
    this.tuesday = tuesday;
  }

  public Day getWednesday() {
    return wednesday;
  }

  public void setWednesday(Day wednesday) {
    this.wednesday = wednesday;
  }

  public Day getThursday() {
    return thursday;
  }

  public void setThursday(Day thursday) {
    this.thursday = thursday;
  }

  public Day getFriday() {
    return friday;
  }

  public void setFriday(Day friday) {
    this.friday = friday;
  }

  public Day getSaturday() {
    return saturday;
  }

  public void setSaturday(Day saturday) {
    this.saturday = saturday;
  }
}
