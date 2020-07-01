package io.github.incplusplus.betterstat.web.mappers;

import io.github.incplusplus.betterstat.persistence.model.Schedule;
import io.github.incplusplus.betterstat.web.dto.ScheduleDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
  ScheduleDto toDTO(Schedule schedule);

  Schedule fromDto(ScheduleDto scheduleDto);

  List<ScheduleDto> mapSchedulesToScheduleDto(List<Schedule> schedules);
}
