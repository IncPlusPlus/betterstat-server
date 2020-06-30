package io.github.incplusplus.betterstat.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Thermostat {

  @Id private String id;
  private boolean setUp;
  private String name;
  private boolean heatingSupported;
  private boolean airConditioningSupported;
  private boolean fanSupported;
  private FanSetting fanSetting;
  private States state;
  @DBRef private Schedule schedule;

  @SuppressWarnings("unused")
  public Thermostat() {
    super();
  }

  public Thermostat(
      String id,
      boolean setUp,
      String name,
      boolean heatingSupported,
      boolean airConditioningSupported,
      boolean fanSupported,
      FanSetting fanSetting,
      States state,
      Schedule schedule) {
    this.id = id;
    this.setUp = setUp;
    this.name = name;
    this.heatingSupported = heatingSupported;
    this.airConditioningSupported = airConditioningSupported;
    this.fanSupported = fanSupported;
    this.fanSetting = fanSetting;
    this.state = state;
    this.schedule = schedule;
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

  public Schedule getSchedule() {
    return schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Thermostat that = (Thermostat) o;

    return new EqualsBuilder()
        .append(isSetUp(), that.isSetUp())
        .append(isHeatingSupported(), that.isHeatingSupported())
        .append(isAirConditioningSupported(), that.isAirConditioningSupported())
        .append(isFanSupported(), that.isFanSupported())
        .append(getId(), that.getId())
        .append(getName(), that.getName())
        .append(getFanSetting(), that.getFanSetting())
        .append(getState(), that.getState())
        .append(getSchedule(), that.getSchedule())
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
        .append(getSchedule())
        .toHashCode();
  }

  @Override
  public String toString() {
    return "Thermostat{"
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
        + ", schedule="
        + schedule
        + '}';
  }
}
