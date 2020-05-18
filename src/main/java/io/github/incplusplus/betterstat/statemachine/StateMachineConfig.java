package io.github.incplusplus.betterstat.statemachine;

import io.github.incplusplus.betterstat.persistence.model.Event;
import io.github.incplusplus.betterstat.persistence.model.States;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.repositories.MongoStateRepository;
import java.util.EnumSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.common.annotation.AnnotationBuilder;
import org.springframework.statemachine.data.mongodb.MongoDbPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.mongodb.MongoDbRepositoryStateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Event> {

  @Autowired private MongoStateRepository mongoStateRepository;

  @Override
  public void configure(StateMachineConfigBuilder<States, Event> config) throws Exception {
    super.configure(config);
  }

  @Override
  public void configure(StateMachineModelConfigurer<States, Event> model) throws Exception {
    super.configure(model);
  }

  @Override
  public void configure(StateMachineConfigurationConfigurer<States, Event> config)
      throws Exception {
    super.configure(config);
  }

  @Override
  public void configure(StateMachineStateConfigurer<States, Event> states) throws Exception {
    states.withStates().initial(States.IDLE).states(EnumSet.allOf(States.class));
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<States, Event> transitions)
      throws Exception {
    transitions
        .withExternal()
        .source(States.IDLE)
        .target(States.COOLING)
        .event(Event.TOO_HOT)
        .and()
        .withExternal()
        .source(States.IDLE)
        .target(States.HEATING)
        .event(Event.TOO_COLD)
        .and()
        .withExternal()
        .source(States.COOLING)
        .target(States.IDLE)
        .event(Event.TARGET_TEMP_REACHED)
        .and()
        .withExternal()
        .source(States.HEATING)
        .target(States.IDLE)
        .event(Event.TARGET_TEMP_REACHED);
  }

  @Override
  public boolean isAssignable(
      AnnotationBuilder<org.springframework.statemachine.config.StateMachineConfig<States, Event>>
          builder) {
    return super.isAssignable(builder);
  }

  @Bean
  public MongoDbRepositoryStateMachinePersist<States, Event>
      mongoDbRepositoryStateMachinePersist() {
    return new MongoDbRepositoryStateMachinePersist<>(mongoStateRepository);
  }

  @Bean
  public MongoDbPersistingStateMachineInterceptor<States, Event, Thermostat> interceptor() {
    return new MongoDbPersistingStateMachineInterceptor<>(mongoDbRepositoryStateMachinePersist());
  }

  @Bean
  public StateMachinePersister<States, Event, String> stateMachinePersister() {
    return new DefaultStateMachinePersister(mongoDbRepositoryStateMachinePersist());
  }
}
