package io.github.incplusplus.betterstat.statemachine;

import io.github.incplusplus.betterstat.persistence.repositories.ThermostatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class EntityPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {
	private final static Logger logger = LoggerFactory.getLogger(EntityPersistStateChangeListener.class);
	
	private final ThermostatRepository thermostatRepository;
	
	@Autowired
	public EntityPersistStateChangeListener(
			ThermostatRepository thermostatRepository) {this.thermostatRepository = thermostatRepository;}
	
	@Override
	public void onPersist(State<String, String> state, Message<String> message, Transition<String, String> transition,
	                      StateMachine<String, String> stateMachine) {
		//		Thermostat thermostat =message.getHeaders().get(THERMOSTAT_HEADER,Thermostat.class);
		//		thermostat.setst
		//		logger.debug("Persisting: the new thermostat.. {}", thermostat);
		//		thermostatRepository.save(thermostat);
	}
}
