package io.github.incplusplus.betterstat.persistence.repositories;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ThermostatRepository extends MongoRepository<Thermostat, UUID> {
}
