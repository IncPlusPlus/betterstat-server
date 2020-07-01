package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.persistence.model.Schedule;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
  Schedule createSchedule(Schedule schedule);

  /**
   * Update the properties of a {@link Schedule}. This will persist the provided schedule with the
   * given id. This will not change any data of the {@linkplain Day}s contained within this
   * Schedule. It will merely change the Day ids that are referenced in this Schedule.
   *
   * @param id the id of the Schedule to update
   * @param schedule the Schedule whose ID already matches an existing Schedule
   * @return the updated Schedule
   * @throws ObjectNotFoundException if there is no {@link Schedule} known by the provided id
   */
  Schedule updateSchedule(String id, Schedule schedule) throws ObjectNotFoundException;

  Optional<Schedule> getScheduleById(String id);

  List<Schedule> findAll();

  boolean existsById(String id);

  Schedule deleteById(String id) throws ObjectNotFoundException;
}
