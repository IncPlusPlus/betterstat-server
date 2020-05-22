package io.github.incplusplus.betterstat.persistence.model;

import org.springframework.data.annotation.Id;

public class Thermostat {

  @Id private String id;
  private String name;
  private boolean heatingSupported;
  private boolean airConditioningSupported;
  private boolean fanSupported;
  private FanSetting fanSetting;
  private States state;
  /** An array of 7 schedules representing the seven days of the week (with Sunday at position 0) */
  private Schedule[] schedules;

  @SuppressWarnings("unused")
  public Thermostat() {
    super();
  }

  public Thermostat(
      String id,
      String name,
      boolean heatingSupported,
      boolean airConditioningSupported,
      boolean fanSupported) {
    this.id = id;
    this.name = name;
    this.heatingSupported = heatingSupported;
    this.airConditioningSupported = airConditioningSupported;
    this.fanSupported = fanSupported;
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

  public Schedule[] getSchedules() {
    return schedules;
  }

  public void setSchedules(Schedule[] schedules) {
    this.schedules = schedules;
  }

  @Override
  public String toString() {
    return "Thermostat{"
        + ", id:'"
        + id
        + '\''
        + ", name:'"
        + name
        + '\''
        + ", heatingSupported:"
        + heatingSupported
        + ", airConditioningSupported:"
        + airConditioningSupported
        + ", fanSupported:"
        + fanSupported
        + '}';
  }
}
