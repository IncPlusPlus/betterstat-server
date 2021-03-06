package io.github.incplusplus.betterstat.persistence.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class StatusReport {

  @DBRef Thermostat thermostat;
  @NotNull @Id private String id;
  private String mostRecentIp;
  private LocalDateTime dateTimeAccordingToDevice;
  private LocalDateTime dateTimeReportReceived;
  private BigDecimal temperature;
  private States currentState;

  public StatusReport() {}

  public StatusReport(
      Thermostat thermostat,
      @NotNull String id,
      String mostRecentIp,
      LocalDateTime dateTimeAccordingToDevice,
      LocalDateTime dateTimeReportReceived,
      BigDecimal temperature,
      States currentState) {
    this.thermostat = thermostat;
    this.id = id;
    this.mostRecentIp = mostRecentIp;
    this.dateTimeAccordingToDevice = dateTimeAccordingToDevice;
    this.dateTimeReportReceived = dateTimeReportReceived;
    this.temperature = temperature;
    this.currentState = currentState;
  }

  public Thermostat getThermostat() {
    return thermostat;
  }

  public void setThermostat(Thermostat thermostat) {
    this.thermostat = thermostat;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMostRecentIp() {
    return mostRecentIp;
  }

  public void setMostRecentIp(String mostRecentIp) {
    this.mostRecentIp = mostRecentIp;
  }

  public LocalDateTime getDateTimeAccordingToDevice() {
    return dateTimeAccordingToDevice;
  }

  public void setDateTimeAccordingToDevice(LocalDateTime dateTimeAccordingToDevice) {
    this.dateTimeAccordingToDevice = dateTimeAccordingToDevice;
  }

  public LocalDateTime getDateTimeReportReceived() {
    return dateTimeReportReceived;
  }

  public void setDateTimeReportReceived(LocalDateTime dateTimeReportReceived) {
    this.dateTimeReportReceived = dateTimeReportReceived;
  }

  public BigDecimal getTemperature() {
    return temperature;
  }

  public void setTemperature(BigDecimal temperature) {
    this.temperature = temperature;
  }

  public States getCurrentState() {
    return currentState;
  }

  public void setCurrentState(States currentState) {
    this.currentState = currentState;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StatusReport that = (StatusReport) o;

    return new EqualsBuilder()
        .append(getThermostat(), that.getThermostat())
        .append(getId(), that.getId())
        .append(getMostRecentIp(), that.getMostRecentIp())
        .append(getDateTimeAccordingToDevice(), that.getDateTimeAccordingToDevice())
        .append(getDateTimeReportReceived(), that.getDateTimeReportReceived())
        .append(getTemperature(), that.getTemperature())
        .append(getCurrentState(), that.getCurrentState())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getThermostat())
        .append(getId())
        .append(getMostRecentIp())
        .append(getDateTimeAccordingToDevice())
        .append(getDateTimeReportReceived())
        .append(getTemperature())
        .append(getCurrentState())
        .toHashCode();
  }
}
