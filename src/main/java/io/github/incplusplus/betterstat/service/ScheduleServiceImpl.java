package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.model.DayOfWeek;
import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.persistence.model.Schedule;
import io.github.incplusplus.betterstat.persistence.repositories.ScheduleRepository;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final DayService dayService;

  @Autowired
  public ScheduleServiceImpl(ScheduleRepository scheduleRepository, DayService dayService) {
    this.scheduleRepository = scheduleRepository;
    this.dayService = dayService;
  }

  @Override
  public Schedule createSchedule(Schedule schedule) {
    return scheduleRepository.save(schedule);
  }

  @Override
  public Schedule updateSchedule(String id, Schedule schedule) throws ObjectNotFoundException {
    if (!this.existsById(id)) throw new ObjectNotFoundException(id, Schedule.class);
    // If it already exists, set the ID of the DTO to the ID we know exists in our repository.
    schedule.setId(id);
    // Saving an object with an ID that already exists will simply update it.
    // https://stackoverflow.com/a/56207430/1687436
    return scheduleRepository.save(schedule);
  }

  @Override
  public Optional<Schedule> getScheduleById(String id) {
    return scheduleRepository.findById(id);
  }

  @Override
  public List<Schedule> findAll() {
    return scheduleRepository.findAll();
  }

  @Override
  public boolean existsById(String id) {
    return scheduleRepository.existsById(id);
  }

  @Override
  public Schedule deleteById(String id) throws ObjectNotFoundException {
    Optional<Schedule> scheduleOptional = this.getScheduleById(id);
    if (scheduleOptional.isEmpty()) {
      throw new ObjectNotFoundException(id, Schedule.class);
    }
    scheduleRepository.deleteById(id);
    return scheduleOptional.get();
  }

  @Override
  public Schedule setDayOfWeek(String scheduleId, DayOfWeek dayOfWeek, String dayId)
      throws ObjectNotFoundException {
    Optional<Day> dayOptional = dayService.getDayById(dayId);
    Optional<Schedule> scheduleOptional = this.getScheduleById(scheduleId);
    if (dayOptional.isEmpty()) {
      throw new ObjectNotFoundException(dayId, Day.class);
    }
    if (scheduleOptional.isEmpty()) {
      throw new ObjectNotFoundException(scheduleId, Schedule.class);
    }
    Day day = dayOptional.get();
    Schedule schedule = scheduleOptional.get();

    /*
     * TODO: Change to a switch expression when google-java-format 1.9 comes out. The fix for switch expression crashes came after 1.8's release. https://github.com/google/google-java-format/compare/google-java-format-1.8...master
     *  Once 1.9 comes out, then a new version of fmt-maven-plugin needs to be released as well.
     */
    switch (dayOfWeek) {
      case Sunday:
        schedule.setSunday(day);
        break;
      case Monday:
        schedule.setMonday(day);
        break;
      case Tuesday:
        schedule.setTuesday(day);
        break;
      case Wednesday:
        schedule.setWednesday(day);
        break;
      case Thursday:
        schedule.setThursday(day);
        break;
      case Friday:
        schedule.setFriday(day);
        break;
      case Saturday:
        schedule.setSaturday(day);
        break;
    }
    return this.updateSchedule(scheduleId, schedule);
  }
}
