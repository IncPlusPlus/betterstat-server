package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThermostatService {
	Thermostat createThermostat(Thermostat thermostat);
	
	Optional<Thermostat> getThermostatById(UUID id);
	
	List<Thermostat> findAll();
}
