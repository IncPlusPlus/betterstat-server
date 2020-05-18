package io.github.incplusplus.betterstat.statemachine;

import io.github.incplusplus.betterstat.persistence.model.Event;
import io.github.incplusplus.betterstat.persistence.model.States;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.repositories.ThermostatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

@Component
public class StateHandler {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private StateMachineFactory<States, Event> thermostatStateMachineFactory;

  @Autowired private StateMachinePersister<States, Event, String> persister;
  @Autowired private ThermostatRepository thermostatRepository;

  public boolean sendEvent(Message<Event> message, Thermostat thermostat) throws Exception {
    boolean result;
    StateMachine<States, Event> thermostatStateMachine =
        thermostatStateMachineFactory.getStateMachine(thermostat.getId());
    thermostatStateMachine.start();
    try {
      persister.restore(thermostatStateMachine, thermostat.getId());
      result = thermostatStateMachine.sendEvent(message);
      persister.persist(thermostatStateMachine, thermostat.getId());
      thermostatRepository.save(thermostat);
      return result;
    } finally {
      thermostatStateMachine.stop();
    }
  }

  public Thermostat createThermostatStateMachine(Thermostat thermostat) throws Exception {
    StateMachine<States, Event> thermostatStateMachine =
        thermostatStateMachineFactory.getStateMachine(thermostat.getId());
    thermostatStateMachine.start();
    thermostat.setState(thermostatStateMachine.getState().getId());
    try {
      Thermostat persistedThermostat = thermostatRepository.save(thermostat);
      persister.persist(thermostatStateMachine, persistedThermostat.getId());
      return persistedThermostat;
    } finally {
      thermostatStateMachine.stop();
    }
  }
}
