package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Schedule;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {

  Schedule createSchedule(Schedule schedule);

  Optional<Schedule> getScheduleById(String id);

  List<Schedule> findAll();

  boolean existsById(String id);

  void deleteById(String id);
}
