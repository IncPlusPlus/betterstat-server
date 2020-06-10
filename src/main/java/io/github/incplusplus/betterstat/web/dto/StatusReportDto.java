package io.github.incplusplus.betterstat.web.dto;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.incplusplus.betterstat.persistence.model.States;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StatusReportDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private String id;

  private String mostRecentIp;
  private LocalDateTime dateTimeAccordingToDevice;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(accessMode = READ_ONLY)
  private LocalDateTime dateTimeReportReceived;

  private BigDecimal temperature;
  private States currentState;

  public StatusReportDto() {
  }

  public StatusReportDto(
      String id,
      String mostRecentIp,
      LocalDateTime dateTimeAccordingToDevice,
      LocalDateTime dateTimeReportReceived,
      BigDecimal temperature,
      States currentState) {
    this.id = id;
    this.mostRecentIp = mostRecentIp;
    this.dateTimeAccordingToDevice = dateTimeAccordingToDevice;
    this.dateTimeReportReceived = dateTimeReportReceived;
    this.temperature = temperature;
    this.currentState = currentState;
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
}
