package io.github.incplusplus.betterstat.web.controller;

import io.github.incplusplus.betterstat.model.ThermostatSetupStatus;
import io.github.incplusplus.betterstat.service.ThermostatSetupService;
import io.github.incplusplus.betterstat.service.ThermostatSetupServiceImpl.DescriptivePortNameSystemPortNamePair;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thermostat/{id}/setup")
public class ThermostatSetupController {

  private final ThermostatSetupService setupService;

  @Autowired
  public ThermostatSetupController(ThermostatSetupService setupService) {
    this.setupService = setupService;
  }

  @GetMapping("/status")
  public ThermostatSetupStatus getCurrentSetupStep(@PathVariable("id") String thermostatId) {
    return setupService.getCurrentSetupStatus(thermostatId);
  }

  @PutMapping("/init")
  public void initSetup(@PathVariable("id") String thermostatId) throws ObjectNotFoundException {
    setupService.initSetup(thermostatId);
  }

  @GetMapping("/ports")
  public List<DescriptivePortNameSystemPortNamePair> getPorts(
      @SuppressWarnings({"unused", "RedundantSuppression"}) @PathVariable("id")
          String thermostatId) {
    return setupService.getPorts();
  }

  @PutMapping("/portConnect")
  public boolean connectToPort(
      @PathVariable("id") String thermostatId, @RequestBody String systemPortName) {
    return setupService.connectToSystemPort(systemPortName, thermostatId);
  }

  @PutMapping("/wifiSsid")
  public void setWifiSsid(@PathVariable("id") String thermostatId, @RequestBody String ssid) {
    setupService.setSsid(thermostatId, ssid);
  }

  @PutMapping("/wifiPassword")
  public void setWifiPassword(
      @PathVariable("id") String thermostatId, @RequestBody String password) {
    setupService.setPassword(thermostatId, password);
  }

  @PutMapping("/serverHostname")
  public void setServerHostname(
      @PathVariable("id") String thermostatId,
      @RequestBody String serverHostname,
      @RequestParam boolean hostnameIsAnIP,
      @RequestParam boolean isSecure,
      @RequestParam int port) {
    setupService.setServerHostname(thermostatId, serverHostname, hostnameIsAnIP, isSecure, port);
  }

  @PutMapping("/begin")
  public boolean beginSetup(@PathVariable("id") String thermostatId)
      throws ObjectNotFoundException {
    return setupService.beginSetup(thermostatId);
  }

  // Calling this will remove the setup helper from the helpers map, making the /status endpoint no
  // longer work.
  // Obviously, this means that you should confirm the /status before blindly calling /complete
  @PutMapping("/complete")
  public void completeSetup(@PathVariable("id") String thermostatId)
      throws ObjectNotFoundException {
    setupService.completeSetup(thermostatId);
  }
}
