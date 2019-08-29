package io.github.incplusplus.thermostat.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "io.github.incplusplus.thermostat.task" })
public class SpringTaskConfig {

}
