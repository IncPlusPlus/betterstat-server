package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;

public interface DayService {
  Day createDay(Day day);

  /**
   * Update the properties of a {@link Day}. Do not use this with untrusted data.
   *
   * @param id the id of the Day to update
   * @param day the Day whose ID already matches an existing Day
   * @return the updated Day
   * @throws ObjectNotFoundException if there is no {@link Day} known by the provided id
   */
  Day updateDay(String id, Day day) throws ObjectNotFoundException;

  Optional<Day> getDayById(String id);

  List<Day> findAll();

  boolean existsById(String id);

  Day deleteById(String id) throws ObjectNotFoundException;
}
