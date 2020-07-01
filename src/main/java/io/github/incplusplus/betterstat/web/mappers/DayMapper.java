package io.github.incplusplus.betterstat.web.mappers;

import io.github.incplusplus.betterstat.persistence.model.Day;
import io.github.incplusplus.betterstat.web.dto.DayDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DayMapper {
  DayDto toDTO(Day day);

  Day fromDto(DayDto dayDto);

  List<DayDto> mapDaysToDayDto(List<Day> days);
}
