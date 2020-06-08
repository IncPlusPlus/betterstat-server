package io.github.incplusplus.betterstat.persistence.model;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.NavigableSet;
import java.util.TreeSet;
import org.springframework.data.annotation.Id;

/**
 * A Day is a collection of points in time that are paired with set points. A single Day represents
 * a single day.
 */
public class Day {
  @Id private String id;
  private String name;
  private NavigableSet<SetPointTimeTuple> times = new TreeSet<>();

  @SuppressWarnings("unused")
  public Day() {}

  public Day(String id, String name, NavigableSet<SetPointTimeTuple> times) {
    this(id, name);
    this.times = times;
  }

  public Day(String id, String name) {
    this.id = id;
    this.name = name;
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

  public NavigableSet<SetPointTimeTuple> getTimes() {
    return times;
  }

  public void setTimes(NavigableSet<SetPointTimeTuple> times) {
    this.times = times;
  }

  /**
   * A two-tuple containing a set point and the time it should activate itself. This class
   * implements {@link Comparable} and compares itself using {@link LocalTime#compareTo(LocalTime)}.
   */
  public static class SetPointTimeTuple implements Comparable<SetPointTimeTuple> {

    /** The set point in degrees celsius. */
    private BigDecimal setPoint;

    private LocalTime time;

    @SuppressWarnings("unused")
    public SetPointTimeTuple() {}

    public SetPointTimeTuple(BigDecimal setPoint, LocalTime time) {
      this.setPoint = setPoint;
      this.time = time;
    }

    public BigDecimal getSetPoint() {
      return setPoint;
    }

    public void setSetPoint(BigDecimal setPoint) {
      this.setPoint = setPoint;
    }

    public LocalTime getTime() {
      return time;
    }

    public void setTime(LocalTime time) {
      this.time = time;
    }

    @Override
    public int compareTo(SetPointTimeTuple other) {
      return this.getTime().compareTo(other.getTime());
    }
  }
}
