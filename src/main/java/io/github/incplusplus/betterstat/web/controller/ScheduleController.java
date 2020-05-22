package io.github.incplusplus.betterstat.web.controller;

import io.github.incplusplus.betterstat.persistence.model.Schedule;
import io.github.incplusplus.betterstat.service.ScheduleService;
import io.github.incplusplus.betterstat.web.dto.ScheduleDto;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import io.github.incplusplus.betterstat.web.mappers.ScheduleMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "The APIs that a user will interact with")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
  private final ScheduleService scheduleService;
  private final ScheduleMapper scheduleMapper;

  @Autowired
  public ScheduleController(ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
    this.scheduleService = scheduleService;
    this.scheduleMapper = scheduleMapper;
  }

  @GetMapping
  public List<ScheduleDto> getAllSchedules() {
    return scheduleMapper.mapSchedulesToScheduleDto(scheduleService.findAll());
  }

  @GetMapping("/{id}")
  public ScheduleDto getSchedule(@PathVariable String id) throws ObjectNotFoundException {
    Optional<Schedule> scheduleOptional = scheduleService.getScheduleById(id);
    if (scheduleOptional.isEmpty()) {
      throw new ObjectNotFoundException(id, Schedule.class);
    }
    return scheduleMapper.toDTO(scheduleOptional.get());
  }

  @PostMapping
  public ScheduleDto createNewSchedule(@RequestBody ScheduleDto scheduleDto) {
    return scheduleMapper.toDTO(
        scheduleService.createSchedule(scheduleMapper.fromDto(scheduleDto)));
  }

  @PutMapping("/{id}")
  public ScheduleDto updateSchedule(@PathVariable String id, @RequestBody ScheduleDto scheduleDto)
      throws ObjectNotFoundException {
    if (!scheduleService.existsById(id)) throw new ObjectNotFoundException(id, Schedule.class);
    // If it already exists, set the ID of the DTO to the ID we know exists in our repository.
    scheduleDto.setId(id);
    // Saving an object with an ID that already exists will simply update it.
    // https://stackoverflow.com/a/56207430/1687436
    return scheduleMapper.toDTO(
        scheduleService.createSchedule(scheduleMapper.fromDto(scheduleDto)));
  }

  @DeleteMapping("/{id}")
  public ScheduleDto deleteSchedule(@PathVariable String id) throws ObjectNotFoundException {
    Optional<Schedule> scheduleOptional = scheduleService.getScheduleById(id);
    if (scheduleOptional.isEmpty()) {
      throw new ObjectNotFoundException(id, Schedule.class);
    }
    scheduleService.deleteById(id);
    return scheduleMapper.toDTO(scheduleOptional.get());
  }
}
