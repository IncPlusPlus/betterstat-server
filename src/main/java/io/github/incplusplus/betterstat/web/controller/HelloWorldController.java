package io.github.incplusplus.betterstat.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hi")
public class HelloWorldController {

  @GetMapping
  public String greet() {
    return "Hi there!";
  }
}
