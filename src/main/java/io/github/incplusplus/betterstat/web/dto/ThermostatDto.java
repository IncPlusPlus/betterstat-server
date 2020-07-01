package io.github.incplusplus.betterstat.web.dto;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.incplusplus.betterstat.persistence.model.FanSetting;
import io.github.incplusplus.betterstat.persistence.model.States;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ThermostatDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private String id;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private boolean setUp;

  private String name;
  private boolean heatingSupported;
  private boolean airConditioningSupported;
  private boolean fanSupported;
  // TODO: Should this be read only?
  private FanSetting fanSetting;
  /*
   * TODO: Instead of having an @Valid NON-DTO class be a field in a DTO, just don't expose that at all.
   *  Only allow manipulation of the Schedule associated with a Thermostat through specific endpoints
   *  which are meant to provide that functionality. Do this for the other DTO classes too.
   */
  //  @Valid private Schedule schedule;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private States state;

  @SuppressWarnings("unused")
  public ThermostatDto() {
    super();
  }

  public ThermostatDto(
      String id,
      boolean setUp,
      String name,
      boolean heatingSupported,
      boolean airConditioningSupported,
      boolean fanSupported,
      FanSetting fanSetting,
      States state) {
    this.id = id;
    this.setUp = setUp;
    this.name = name;
    this.heatingSupported = heatingSupported;
    this.airConditioningSupported = airConditioningSupported;
    this.fanSupported = fanSupported;
    this.fanSetting = fanSetting;
    this.state = state;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isSetUp() {
    return setUp;
  }

  public void setSetUp(boolean setUp) {
    this.setUp = setUp;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isHeatingSupported() {
    return heatingSupported;
  }

  public void setHeatingSupported(boolean heatingSupported) {
    this.heatingSupported = heatingSupported;
  }

  public boolean isAirConditioningSupported() {
    return airConditioningSupported;
  }

  public void setAirConditioningSupported(boolean airConditioningSupported) {
    this.airConditioningSupported = airConditioningSupported;
  }

  public boolean isFanSupported() {
    return fanSupported;
  }

  public void setFanSupported(boolean fanSupported) {
    this.fanSupported = fanSupported;
  }

  public FanSetting getFanSetting() {
    return fanSetting;
  }

  public void setFanSetting(FanSetting fanSetting) {
    this.fanSetting = fanSetting;
  }

  public States getState() {
    return state;
  }

  public void setState(States state) {
    this.state = state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ThermostatDto that = (ThermostatDto) o;

    return new EqualsBuilder()
        .append(isSetUp(), that.isSetUp())
        .append(isHeatingSupported(), that.isHeatingSupported())
        .append(isAirConditioningSupported(), that.isAirConditioningSupported())
        .append(isFanSupported(), that.isFanSupported())
        .append(getId(), that.getId())
        .append(getName(), that.getName())
        .append(getFanSetting(), that.getFanSetting())
        .append(getState(), that.getState())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(isSetUp())
        .append(getName())
        .append(isHeatingSupported())
        .append(isAirConditioningSupported())
        .append(isFanSupported())
        .append(getFanSetting())
        .append(getState())
        .toHashCode();
  }

  @Override
  public String toString() {
    return "ThermostatDto{"
        + "id='"
        + id
        + '\''
        + ", setUp="
        + setUp
        + ", name='"
        + name
        + '\''
        + ", heatingSupported="
        + heatingSupported
        + ", airConditioningSupported="
        + airConditioningSupported
        + ", fanSupported="
        + fanSupported
        + ", fanSetting="
        + fanSetting
        + ", state="
        + state
        + '}';
  }
}
