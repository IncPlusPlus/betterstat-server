package io.github.incplusplus.betterstat.service;

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

  @Autowired
  public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
    this.scheduleRepository = scheduleRepository;
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
}
