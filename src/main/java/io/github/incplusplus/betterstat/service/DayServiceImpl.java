package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.persistence.repositories.DayRepository;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DayServiceImpl implements DayService {
  private final DayRepository dayRepository;

  @Autowired
  public DayServiceImpl(DayRepository dayRepository) {
    this.dayRepository = dayRepository;
  }

  @Override
  public Day createDay(Day day) {
    return dayRepository.save(day);
  }

  @Override
  public Day updateDay(String id, Day day) throws ObjectNotFoundException {
    if (!this.existsById(id)) throw new ObjectNotFoundException(id, Day.class);
    // If it already exists, set the ID of the DTO to the ID we know exists in our repository.
    day.setId(id);
    // Saving an object with an ID that already exists will simply update it.
    // https://stackoverflow.com/a/56207430/1687436
    return dayRepository.save(day);
  }

  @Override
  public Optional<Day> getDayById(String id) {
    return dayRepository.findById(id);
  }

  @Override
  public List<Day> findAll() {
    return dayRepository.findAll();
  }

  @Override
  public boolean existsById(String id) {
    return dayRepository.existsById(id);
  }

  @Override
  public Day deleteById(String id) throws ObjectNotFoundException {
    Optional<Day> dayOptional = this.getDayById(id);
    if (dayOptional.isEmpty()) {
      throw new ObjectNotFoundException(id, Day.class);
    }
    dayRepository.deleteById(id);
    return dayOptional.get();
  }
}
