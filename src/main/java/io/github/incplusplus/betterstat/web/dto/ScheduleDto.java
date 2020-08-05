package io.github.incplusplus.betterstat.web.dto;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ScheduleDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private String id;

  private String name;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private DayDto sunday;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private DayDto monday;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private DayDto tuesday;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private DayDto wednesday;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private DayDto thursday;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private DayDto friday;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private DayDto saturday;

  @SuppressWarnings("unused")
  public ScheduleDto() {}

  public ScheduleDto(
      String id,
      String name,
      DayDto sunday,
      DayDto monday,
      DayDto tuesday,
      DayDto wednesday,
      DayDto thursday,
      DayDto friday,
      DayDto saturday) {
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

  public DayDto getSunday() {
    return sunday;
  }

  public void setSunday(DayDto sunday) {
    this.sunday = sunday;
  }

  public DayDto getMonday() {
    return monday;
  }

  public void setMonday(DayDto monday) {
    this.monday = monday;
  }

  public DayDto getTuesday() {
    return tuesday;
  }

  public void setTuesday(DayDto tuesday) {
    this.tuesday = tuesday;
  }

  public DayDto getWednesday() {
    return wednesday;
  }

  public void setWednesday(DayDto wednesday) {
    this.wednesday = wednesday;
  }

  public DayDto getThursday() {
    return thursday;
  }

  public void setThursday(DayDto thursday) {
    this.thursday = thursday;
  }

  public DayDto getFriday() {
    return friday;
  }

  public void setFriday(DayDto friday) {
    this.friday = friday;
  }

  public DayDto getSaturday() {
    return saturday;
  }

  public void setSaturday(DayDto saturday) {
    this.saturday = saturday;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ScheduleDto that = (ScheduleDto) o;

    return new EqualsBuilder()
        .append(getId(), that.getId())
        .append(getName(), that.getName())
        .append(getSunday(), that.getSunday())
        .append(getMonday(), that.getMonday())
        .append(getTuesday(), that.getTuesday())
        .append(getWednesday(), that.getWednesday())
        .append(getThursday(), that.getThursday())
        .append(getFriday(), that.getFriday())
        .append(getSaturday(), that.getSaturday())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(getName())
        .append(getSunday())
        .append(getMonday())
        .append(getTuesday())
        .append(getWednesday())
        .append(getThursday())
        .append(getFriday())
        .append(getSaturday())
        .toHashCode();
  }

  @Override
  public String toString() {
    return "ScheduleDto{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", sunday="
        + sunday
        + ", monday="
        + monday
        + ", tuesday="
        + tuesday
        + ", wednesday="
        + wednesday
        + ", thursday="
        + thursday
        + ", friday="
        + friday
        + ", saturday="
        + saturday
        + '}';
  }
}
