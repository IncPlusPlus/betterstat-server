package io.github.incplusplus.betterstat.web.controller;

import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.service.StatusReportService;
import io.github.incplusplus.betterstat.service.ThermostatService;
import io.github.incplusplus.betterstat.web.dto.StatusReportDto;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import io.github.incplusplus.betterstat.web.mappers.StatusReportMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Device API", description = "The APIs that thermostat devices will interact with")
@RestController
@RequestMapping("/thermostat-api/{thermostatId}/status")
public class ThermostatStatusController {

  private final StatusReportService statusReportService;
  private final ThermostatService thermostatService;
  private final StatusReportMapper mapper;

  @Autowired
  public ThermostatStatusController(
      StatusReportService statusReportService,
      ThermostatService thermostatService,
      StatusReportMapper mapper) {
    this.statusReportService = statusReportService;
    this.thermostatService = thermostatService;
    this.mapper = mapper;
  }

  @GetMapping("/all")
  public List<StatusReportDto> getAllFromThermostat(@PathVariable String thermostatId)
      throws ObjectNotFoundException {
    return mapper.mapStatusReportsToStatusReportDto(
        statusReportService.getAllFromThermostat(
            thermostatService
                .getThermostatById(thermostatId)
                .orElseThrow(() -> new ObjectNotFoundException(thermostatId, Thermostat.class))));
  }

  @GetMapping
  public StatusReportDto getMostRecentStatusReportFromThermostat(@PathVariable String thermostatId)
      throws ObjectNotFoundException {
    return mapper.toDTO(
        statusReportService.getMostRecentFromThermostat(
            thermostatService
                .getThermostatById(thermostatId)
                .orElseThrow(() -> new ObjectNotFoundException(thermostatId, Thermostat.class))));
  }

  @PutMapping
  public StatusReportDto sendStatus(
      @PathVariable String thermostatId, @Valid @RequestBody StatusReportDto statusReport) {
    return mapper.toDTO(
        statusReportService.createStatusReport(mapper.fromDto(statusReport, thermostatId)));
  }
}
