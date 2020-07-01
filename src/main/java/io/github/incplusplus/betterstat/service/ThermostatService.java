package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ThermostatService {

  Thermostat createThermostat(Thermostat thermostat) throws Exception;

  /**
   * Update the non-state-machine-related properties of a persisted {@link Thermostat}. Do not use
   * this with untrusted data.
   *
   * @param id the id of the Thermostat to update
   * @param thermostat the Thermostat whose ID already matches an existing Thermostat
   * @return the updated Thermostat
   * @throws ObjectNotFoundException if there is no {@link Thermostat} known by the provided id
   */
  Thermostat updateThermostat(String id, Thermostat thermostat) throws ObjectNotFoundException;

  Optional<Thermostat> getThermostatById(String id);

  List<Thermostat> findAll();

  Thermostat deleteById(String id) throws ObjectNotFoundException;

  boolean existsById(String id);
}
