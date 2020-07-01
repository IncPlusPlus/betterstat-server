package io.github.incplusplus.betterstat.scheduling;

import static java.util.Objects.isNull;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.model.ThermostatApiUser;
import io.github.incplusplus.betterstat.persistence.repositories.ThermostatApiUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PruneApiCredentials {

  private static final Logger logger = LoggerFactory.getLogger(PruneApiCredentials.class);

  private final ThermostatApiUserRepository thermostatApiUserRepository;

  @Autowired
  public PruneApiCredentials(ThermostatApiUserRepository thermostatApiUserRepository) {
    this.thermostatApiUserRepository = thermostatApiUserRepository;
  }

  /**
   * Deletes {@linkplain ThermostatApiUser}s that aren't associated to any existing {@link
   * Thermostat}. Scheduled for every day at noon.
   */
  @Scheduled(cron = "0 0 12 * * *")
  public void pruneOrphanedApiCredentials() {
    List<ThermostatApiUser> orphanedCredentials =
        thermostatApiUserRepository.findAll().stream()
            .filter(thermostatApiUser -> isNull(thermostatApiUser.getThermostat()))
            .collect(Collectors.toList());
    if (!orphanedCredentials.isEmpty()) {
      logger.info("Pruning orphaned API credentials no longer in use by thermostat devices.");
      for (ThermostatApiUser credentials : orphanedCredentials) {
        logger.info("Found ThermostatApiUser which doesn't refer to a Thermostat. Deleting...");
        thermostatApiUserRepository.delete(credentials);
      }
      logger.info("Done pruning.");
    } else {
      logger.info("No orphaned API credentials. Skipping pruning process.");
    }
  }
}
