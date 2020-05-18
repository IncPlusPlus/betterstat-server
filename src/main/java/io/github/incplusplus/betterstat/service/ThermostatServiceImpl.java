package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.repositories.ThermostatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ThermostatServiceImpl implements ThermostatService {
	private final ThermostatRepository thermostatRepository;
	
	@Autowired
	public ThermostatServiceImpl(ThermostatRepository thermostatRepository) {
		this.thermostatRepository = thermostatRepository;
	}
	
	@Override
	public Thermostat createThermostat(Thermostat thermostat) {
		return this.thermostatRepository.save(thermostat);
	}
	
	@Override
	public Optional<Thermostat> getThermostatById(UUID id) {
		return thermostatRepository.findById(id);
	}
	
	@Override
	public List<Thermostat> findAll() {
		return thermostatRepository.findAll();
	}
}
