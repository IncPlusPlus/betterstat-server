package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.StatusReport;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;

public interface StatusReportService {

  StatusReport createStatusReport(StatusReport report);

  Optional<StatusReport> getById(String id);

  List<StatusReport> findAll();

  StatusReport deleteById(String id) throws ObjectNotFoundException;

  List<StatusReport> getAllFromThermostat(Thermostat thermostat);

  StatusReport getMostRecentFromThermostat(Thermostat thermostat);

  boolean existsById(String id);
}
