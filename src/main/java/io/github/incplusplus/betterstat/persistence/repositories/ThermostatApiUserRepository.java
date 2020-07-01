package io.github.incplusplus.betterstat.persistence.repositories;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.model.ThermostatApiUser;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThermostatApiUserRepository extends MongoRepository<ThermostatApiUser, String> {

  ThermostatApiUser findByUsername(String username);

  /**
   * @param id the id of a {@link Thermostat}
   * @return a list of all {@linkplain ThermostatApiUser} whose Thermostat property's id matches the
   *     provided parameter.
   */
  List<ThermostatApiUser> findByThermostat_Id(String id);

  /**
   * Same as {@link ThermostatApiUserRepository#save(Object)} except before saving, this method
   * finds all records of ThermostatApiUser associated with the Thermostat in the provided
   * parameter.
   *
   * @param thermostatApiUser the credentials to persist
   * @return the persisted object
   */
  default ThermostatApiUser exclusiveSave(ThermostatApiUser thermostatApiUser) {
    return this.save(thermostatApiUser);
  }
}
