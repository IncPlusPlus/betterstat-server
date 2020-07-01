package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.repositories.ThermostatRepository;
import io.github.incplusplus.betterstat.statemachine.StateHandler;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
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
  public Thermostat updateThermostat(String id, Thermostat thermostat)
      throws ObjectNotFoundException {
    if (!this.existsById(id)) throw new ObjectNotFoundException(id, Thermostat.class);
    // If it already exists, set the ID of the DTO to the ID we know exists in our repository.
    thermostat.setId(id);
    // Saving an object with an ID that already exists will simply update it.
    // https://stackoverflow.com/a/56207430/1687436
    return thermostatRepository.save(thermostat);
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
  public Thermostat deleteById(String id) throws ObjectNotFoundException {
    Optional<Thermostat> thermostatOptional = this.getThermostatById(id);
    if (thermostatOptional.isEmpty()) {
      throw new ObjectNotFoundException(id, Thermostat.class);
    }
    thermostatRepository.deleteById(id);
    return thermostatOptional.get();
  }

  @Override
  public boolean existsById(String id) {
    return thermostatRepository.existsById(id);
  }
}
