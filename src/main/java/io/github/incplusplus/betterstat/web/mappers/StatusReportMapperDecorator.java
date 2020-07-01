package io.github.incplusplus.betterstat.web.mappers;

import io.github.incplusplus.betterstat.persistence.model.StatusReport;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.web.dto.StatusReportDto;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class StatusReportMapperDecorator implements StatusReportMapper {

  @Autowired
  @Qualifier("delegate")
  private StatusReportMapper delegate;

  @Override
  public StatusReport fromDto(StatusReportDto statusReportDto, String thermostatId) {
    StatusReport report = delegate.fromDto(statusReportDto, thermostatId);
    Thermostat thermostat = new Thermostat();

    thermostat.setId(thermostatId);
    report.setDateTimeReportReceived(LocalDateTime.now());
    report.setThermostat(thermostat);
    return report;
  }
}
