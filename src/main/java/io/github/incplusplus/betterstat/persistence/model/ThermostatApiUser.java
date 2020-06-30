package io.github.incplusplus.betterstat.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ThermostatApiUser {

  @Id String id;
  @DBRef private Thermostat thermostat;
  private String username;
  private String password;

  @SuppressWarnings("unused")
  public ThermostatApiUser() {}

  public ThermostatApiUser(Thermostat thermostat, String username, String password) {
    this.thermostat = thermostat;
    this.username = username;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Thermostat getThermostat() {
    return thermostat;
  }

  public void setThermostat(Thermostat thermostat) {
    this.thermostat = thermostat;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
