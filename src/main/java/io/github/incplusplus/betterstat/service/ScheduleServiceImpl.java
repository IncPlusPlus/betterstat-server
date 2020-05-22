package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Schedule;
import io.github.incplusplus.betterstat.persistence.repositories.ScheduleRepository;
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
  public void deleteById(String id) {
    scheduleRepository.deleteById(id);
  }
}
