package io.github.incplusplus.betterstat.web.controller;

import io.github.incplusplus.betterstat.model.DayOfWeek;
import io.github.incplusplus.betterstat.persistence.model.Schedule;
import io.github.incplusplus.betterstat.service.ScheduleService;
import io.github.incplusplus.betterstat.web.dto.ScheduleDto;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import io.github.incplusplus.betterstat.web.mappers.DayMapper;
import io.github.incplusplus.betterstat.web.mappers.ScheduleMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
  public ScheduleController(
      ScheduleService scheduleService, DayMapper dayMapper, ScheduleMapper scheduleMapper) {
    this.scheduleService = scheduleService;
    this.scheduleMapper = scheduleMapper;
  }

  @GetMapping
  public List<ScheduleDto> getAllSchedules() {
    return scheduleMapper.mapSchedulesToScheduleDto(scheduleService.findAll());
  }

  @GetMapping("/{scheduleId}")
  public ScheduleDto getSchedule(@PathVariable String scheduleId) throws ObjectNotFoundException {
    Optional<Schedule> scheduleOptional = scheduleService.getScheduleById(scheduleId);
    if (scheduleOptional.isEmpty()) {
      throw new ObjectNotFoundException(scheduleId, Schedule.class);
    }
    return scheduleMapper.toDTO(scheduleOptional.get());
  }

  @PostMapping
  public ScheduleDto createNewSchedule(@Valid @RequestBody ScheduleDto scheduleDto) {
    return scheduleMapper.toDTO(
        scheduleService.createSchedule(scheduleMapper.fromDto(scheduleDto)));
  }

  @PutMapping("/{scheduleId}")
  public ScheduleDto updateSchedule(
      @PathVariable String scheduleId, @Valid @RequestBody ScheduleDto scheduleDto)
      throws ObjectNotFoundException {
    return scheduleMapper.toDTO(
        scheduleService.updateSchedule(scheduleId, scheduleMapper.fromDto(scheduleDto)));
  }

  @PutMapping("/{scheduleId}/set{dayOfWeek}")
  public ScheduleDto setDay(
      @PathVariable String scheduleId,
      @NotNull @RequestBody String dayId,
      @PathVariable DayOfWeek dayOfWeek)
      throws ObjectNotFoundException {
    return scheduleMapper.toDTO(scheduleService.setDayOfWeek(scheduleId, dayOfWeek, dayId));
  }

  @DeleteMapping("/{scheduleId}")
  public ScheduleDto deleteSchedule(@PathVariable String scheduleId)
      throws ObjectNotFoundException {
    return scheduleMapper.toDTO(scheduleService.deleteById(scheduleId));
  }
}
