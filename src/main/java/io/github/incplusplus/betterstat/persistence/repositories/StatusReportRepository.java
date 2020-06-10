package io.github.incplusplus.betterstat.persistence.repositories;

import io.github.incplusplus.betterstat.persistence.model.StatusReport;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusReportRepository extends MongoRepository<StatusReport, String> {

  List<StatusReport> findAllByThermostatOrderByDateTimeReportReceived(Thermostat thermostat);

  StatusReport findFirstByThermostatOrderByDateTimeReportReceivedDesc(Thermostat thermostat);
}
