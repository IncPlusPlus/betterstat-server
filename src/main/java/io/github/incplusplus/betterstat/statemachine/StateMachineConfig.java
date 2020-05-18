package io.github.incplusplus.betterstat.statemachine;

import io.github.incplusplus.betterstat.persistence.model.Event;
import io.github.incplusplus.betterstat.persistence.model.States;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.repositories.MongoStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.config.common.annotation.AnnotationBuilder;
import org.springframework.statemachine.data.mongodb.MongoDbPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.mongodb.MongoDbRepositoryStateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;
import java.util.UUID;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Event> {
	@Autowired
	private MongoStateRepository mongoStateRepository;
	
	@Override
	public void configure(StateMachineConfigBuilder<States, Event> config) throws Exception {
		super.configure(config);
	}
	
	@Override
	public void configure(StateMachineModelConfigurer<States, Event> model) throws Exception {
		super.configure(model);
	}
	
	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Event> config) throws Exception {
		super.configure(config);
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<States, Event> states) throws Exception {
		states
				.withStates()
				.initial(States.IDLE)
				.states(EnumSet.allOf(States.class));
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Event> transitions) throws Exception {
		transitions
				.withExternal()
				.source(States.IDLE).target(States.COOLING)
				.event(Event.TOO_HOT)
				.and()
				.withExternal()
				.source(States.IDLE).target(States.HEATING)
				.event(Event.TOO_COLD)
				.and()
				.withExternal()
				.source(States.COOLING).target(States.IDLE)
				.event(Event.TARGET_TEMP_REACHED)
				.and()
				.withExternal()
				.source(States.HEATING).target(States.IDLE)
				.event(Event.TARGET_TEMP_REACHED);
	}
	
	@Override
	public boolean isAssignable(
			AnnotationBuilder<org.springframework.statemachine.config.StateMachineConfig<States, Event>> builder) {
		return super.isAssignable(builder);
	}
	
	//	@Bean
	//	public StateMachineRuntimePersister<States, Event, String> stateMachineRuntimePersister(
	//			MongoDbStateMachineRepository mongoDbStateMachineRepository) {
	//		return new MongoDbPersistingStateMachineInterceptor<>(mongoDbStateMachineRepository);
	//	}
	
	@Bean
	public MongoDbRepositoryStateMachinePersist<States, Event> mongoDbRepositoryStateMachinePersist() {
		return new MongoDbRepositoryStateMachinePersist<>(mongoStateRepository);
	}
	
	@Bean
	public MongoDbPersistingStateMachineInterceptor<States, Event, Thermostat> interceptor() {
		MongoDbPersistingStateMachineInterceptor<States, Event, Thermostat> interceptor = new MongoDbPersistingStateMachineInterceptor<>(
				mongoDbRepositoryStateMachinePersist());
		return interceptor;
	}
	
	@Bean
	public StateMachinePersister<States, Event, UUID> stateMachinePersister() {
		return new DefaultStateMachinePersister(mongoDbRepositoryStateMachinePersist());
	}
}
