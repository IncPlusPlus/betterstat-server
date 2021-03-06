package io.github.incplusplus.betterstat.web.mappers;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.web.dto.ThermostatDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThermostatMapper {

  ThermostatDto toDto(Thermostat thermostat);

  Thermostat fromDto(ThermostatDto thermostatDto);

  List<ThermostatDto> mapThermostatsToThermostatDTO(List<Thermostat> thermostats);
}
