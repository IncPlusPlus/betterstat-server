package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.Schedule;
import io.github.incplusplus.betterstat.persistence.model.StatusReport;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.repositories.StatusReportRepository;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatusReportServiceImpl implements StatusReportService {

  private final StatusReportRepository repository;

  @Autowired
  public StatusReportServiceImpl(StatusReportRepository repository) {
    this.repository = repository;
  }

  @Override
  public StatusReport createStatusReport(StatusReport report) {
    return repository.save(report);
  }

  @Override
  public Optional<StatusReport> getById(String id) {
    return repository.findById(id);
  }

  @Override
  public List<StatusReport> findAll() {
    return repository.findAll();
  }

  @Override
  public StatusReport deleteById(String id) throws ObjectNotFoundException {
    Optional<StatusReport> statusReportOptional = this.getById(id);
    if (statusReportOptional.isEmpty()) {
      throw new ObjectNotFoundException(id, Schedule.class);
    }
    repository.deleteById(id);
    return statusReportOptional.get();
  }

  @Override
  public List<StatusReport> getAllFromThermostat(Thermostat thermostat) {
    return repository.findAllByThermostatOrderByDateTimeReportReceived(thermostat);
  }

  @Override
  public StatusReport getMostRecentFromThermostat(Thermostat thermostat) {
    return repository.findFirstByThermostatOrderByDateTimeReportReceivedDesc(thermostat);
  }

  @Override
  public boolean existsById(String id) {
    return false;
  }
}
