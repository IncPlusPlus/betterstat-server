package io.github.incplusplus.betterstat.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Device API", description = "The APIs that thermostat devices will interact with")
@RestController
@RequestMapping("/thermostat-api")
public class ThermostatConnectionTestController {

  @GetMapping("/hello-world")
  public String helloWorld() {
    return "Ok";
  }
}
