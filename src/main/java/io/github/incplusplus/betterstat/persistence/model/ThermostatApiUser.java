package io.github.incplusplus.betterstat.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ThermostatApiUser that = (ThermostatApiUser) o;

    return new EqualsBuilder()
        .append(getId(), that.getId())
        .append(getThermostat(), that.getThermostat())
        .append(getUsername(), that.getUsername())
        .append(getPassword(), that.getPassword())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(getThermostat())
        .append(getUsername())
        .append(getPassword())
        .toHashCode();
  }

  @Override
  public String toString() {
    return "ThermostatApiUser{"
        + "id='"
        + id
        + '\''
        + ", thermostat="
        + thermostat
        + ", username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
