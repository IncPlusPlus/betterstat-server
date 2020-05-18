package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;

import java.util.List;
import java.util.Optional;

public interface ThermostatService {
	Thermostat createThermostat(Thermostat thermostat) throws Exception;
	
	Optional<Thermostat> getThermostatById(String id);
	
	List<Thermostat> findAll();
	
	void deleteById(String id);
}
