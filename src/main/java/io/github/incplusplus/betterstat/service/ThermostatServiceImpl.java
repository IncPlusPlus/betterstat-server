package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.repositories.ThermostatRepository;
import io.github.incplusplus.betterstat.statemachine.StateHandler;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ThermostatServiceImpl implements ThermostatService {

  private final ThermostatRepository thermostatRepository;
  private final StateHandler stateHandler;

  @Autowired
  public ThermostatServiceImpl(
      ThermostatRepository thermostatRepository, StateHandler stateHandler) {
    this.thermostatRepository = thermostatRepository;
    this.stateHandler = stateHandler;
  }

  @Override
  public Thermostat createThermostat(Thermostat thermostat) throws Exception {
    return stateHandler.createThermostatStateMachine(thermostat);
  }

  @Override
  public Optional<Thermostat> getThermostatById(String id) {
    return thermostatRepository.findById(id);
  }

  @Override
  public List<Thermostat> findAll() {
    return thermostatRepository.findAll();
  }

  @Override
  public void deleteById(String id) {
    thermostatRepository.deleteById(id);
  }
}
