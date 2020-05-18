package io.github.incplusplus.betterstat.web.controller.thermostat;

import io.github.incplusplus.betterstat.persistence.model.Event;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.service.ThermostatService;
import io.github.incplusplus.betterstat.statemachine.StateHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Device API", description = "The APIs that thermostat devices will interact with")
@RestController
@RequestMapping("/thermostat")
public class ThermostatController {
	private final ThermostatService thermostatService;
	private final StateHandler stateHandler;
	
	@Autowired
	public ThermostatController(ThermostatService thermostatService,
	                            StateHandler stateHandler) {
		this.thermostatService = thermostatService;
		this.stateHandler = stateHandler;
	}
	
	@GetMapping
	public List<Thermostat> getAllThermostats() {
		return thermostatService.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Thermostat> getById(@PathVariable UUID id) {
		return thermostatService.getThermostatById(id);
	}
	
	@PutMapping("/{id}/sendEvent")
	public boolean sendEvent(@PathVariable UUID id, @RequestBody Event event) throws Exception {
		Optional<Thermostat> thermostatOptional = thermostatService.getThermostatById(id);
		if (thermostatOptional.isEmpty())
			throw new RuntimeException("No such thermostat");
		Thermostat thermostat = thermostatOptional.get();
		Message<Event> message = MessageBuilder.withPayload(event).setHeader("thermostat", thermostat).build();
		return stateHandler.sendEvent(message, thermostat);
	}
	
	@PostMapping
	public Thermostat addThermostat(@RequestBody Thermostat thermostat) {
		return thermostatService.createThermostat(thermostat);
	}
}
