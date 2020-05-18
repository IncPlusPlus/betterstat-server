package io.github.incplusplus.betterstat.web.mappers;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.web.dto.ThermostatDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThermostatMapper {
	ThermostatDto toDTO(Thermostat thermostat);
	
	Thermostat fromDTO(ThermostatDto thermostatDto);
	
	List<ThermostatDto> mapThermostatsToThermostatDTO(List<Thermostat> thermostats);
}
