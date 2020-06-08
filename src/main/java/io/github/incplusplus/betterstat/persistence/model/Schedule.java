package io.github.incplusplus.betterstat.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Schedule {
  @Id private String id;
  private String name;
  @DBRef private Day sunday;
  @DBRef private Day monday;
  @DBRef private Day tuesday;
  @DBRef private Day wednesday;
  @DBRef private Day thursday;
  @DBRef private Day friday;
  @DBRef private Day saturday;

  @SuppressWarnings("unused")
  public Schedule() {}

  public Schedule(
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
