package io.github.incplusplus.betterstat.persistence.model;

import org.springframework.data.annotation.Id;

public class User {

  @Id private String id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  /** Currently unused */
  private boolean enabled;

  public User() {}

  public User(
      String id,
      String firstName,
      String lastName,
      String email,
      String password,
      boolean enabled) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.enabled = enabled;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
