package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.model.ThermostatSetupStatus;
import io.github.incplusplus.betterstat.service.ThermostatSetupServiceImpl.DescriptivePortNameSystemPortNamePair;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.List;

public interface ThermostatSetupService {

  ThermostatSetupStatus getCurrentSetupStatus(String thermostatId);

  void initSetup(String thermostatId) throws ObjectNotFoundException;

  boolean beginSetup(String thermostatId) throws ObjectNotFoundException;

  List<DescriptivePortNameSystemPortNamePair> getPorts();

  boolean connectToSystemPort(String systemPortName, String thermostatId);

  void setSsid(String thermostatId, String ssid);

  void setPassword(String thermostatId, String password);

  void setServerHostname(
      String thermostatId,
      String serverHostname,
      boolean hostnameIsAnIP,
      boolean isSecure,
      int port);

  void completeSetup(String thermostatId) throws ObjectNotFoundException;
}
