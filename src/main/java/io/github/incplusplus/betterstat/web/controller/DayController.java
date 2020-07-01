package io.github.incplusplus.betterstat.web.controller;

import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.service.DayService;
import io.github.incplusplus.betterstat.web.dto.DayDto;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import io.github.incplusplus.betterstat.web.mappers.DayMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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
@RequestMapping("/day")
public class DayController {
  private final DayService dayService;
  private final DayMapper dayMapper;

  @Autowired
  public DayController(DayService dayService, DayMapper dayMapper) {
    this.dayService = dayService;
    this.dayMapper = dayMapper;
  }

  @GetMapping
  public List<DayDto> getAllDays() {
    return dayMapper.mapDaysToDayDto(dayService.findAll());
  }

  @GetMapping("/{id}")
  public DayDto getDay(@PathVariable String id) throws ObjectNotFoundException {
    Optional<Day> dayOptional = dayService.getDayById(id);
    if (dayOptional.isEmpty()) {
      throw new ObjectNotFoundException(id, Day.class);
    }
    return dayMapper.toDTO(dayOptional.get());
  }

  @PostMapping
  public DayDto createNewDay(@Valid @RequestBody DayDto dayDto) {
    return dayMapper.toDTO(dayService.createDay(dayMapper.fromDto(dayDto)));
  }

  @PutMapping("/{id}")
  public DayDto updateDay(@PathVariable String id, @Valid @RequestBody DayDto dayDto)
      throws ObjectNotFoundException {
    return dayMapper.toDTO(dayService.updateDay(id, dayMapper.fromDto(dayDto)));
  }

  @DeleteMapping("/{id}")
  public DayDto deleteDay(@PathVariable String id) throws ObjectNotFoundException {
    return dayMapper.toDTO(dayService.deleteById(id));
  }
}
