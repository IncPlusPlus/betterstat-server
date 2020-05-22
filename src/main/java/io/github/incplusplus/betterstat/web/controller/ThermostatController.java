package io.github.incplusplus.betterstat.web.controller;

import io.github.incplusplus.betterstat.persistence.model.Event;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.service.ThermostatService;
import io.github.incplusplus.betterstat.statemachine.StateHandler;
import io.github.incplusplus.betterstat.web.dto.ThermostatDto;
import io.github.incplusplus.betterstat.web.mappers.ThermostatMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Device API", description = "The APIs that thermostat devices will interact with")
@RestController
@RequestMapping("/thermostat")
public class ThermostatController {

  private final ThermostatService thermostatService;
  private final StateHandler stateHandler;
  private final ThermostatMapper mapper;

  @Autowired
  public ThermostatController(
      ThermostatService thermostatService, StateHandler stateHandler, ThermostatMapper mapper) {
    this.thermostatService = thermostatService;
    this.stateHandler = stateHandler;
    this.mapper = mapper;
  }

  @GetMapping
  public List<ThermostatDto> getAllThermostats() {
    return mapper.mapThermostatsToThermostatDTO(thermostatService.findAll());
  }

  @GetMapping("/{id}")
  public ThermostatDto getById(@PathVariable String id) {
    return mapper.toDTO(thermostatService.getThermostatById(id).orElseThrow());
  }

  @PutMapping("/{id}/sendEvent")
  public boolean sendEvent(@PathVariable String id, @RequestBody Event event) throws Exception {
    Optional<Thermostat> thermostatOptional = thermostatService.getThermostatById(id);
    if (thermostatOptional.isEmpty()) {
      throw new RuntimeException("No such thermostat");
    }
    Thermostat thermostat = thermostatOptional.get();
    Message<Event> message =
        MessageBuilder.withPayload(event).setHeader("thermostat", thermostat).build();
    return stateHandler.sendEvent(message, thermostat);
  }

  @PostMapping
  public Thermostat addThermostat(@RequestBody Thermostat thermostat) throws Exception {
    return thermostatService.createThermostat(thermostat);
  }

  @DeleteMapping("/{id}")
  public void deleteThermostat(@PathVariable String id) {
    thermostatService.deleteById(id);
  }
}
