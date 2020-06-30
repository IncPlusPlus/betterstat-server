package io.github.incplusplus.betterstat.model;

import io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings;
import io.github.incplusplus.betterstat.web.controller.ThermostatConnectionTestController;
import io.github.incplusplus.betterstat.web.controller.ThermostatSetupController;

/** Describes the current step of the setup process. */
public enum ThermostatSetupStep {
  /** There's been some kind of error, check the messages of the {@link ThermostatSetupStatus} */
  ERROR,
  /**
   * Waiting for the user to plug the thermostat into the server so communication over serial can
   * occur.
   */
  NEED_THERMOSTAT_CONNECTION,
  /**
   * A connection has successfully been made to the device over serial and the device has been sent
   * data and the server is waiting to hear {@link DeviceStrings#STATUS_WAITING_FOR_SETUP} back.
   * This status exists to illustrate that there <i>is</i> a connection but the device and the
   * server aren't on the same page yet.
   */
  WAITING_FOR_DEVICE_RESPONSE,
  /** The device is connected and waiting for configuration. */
  SETUP_READY_TO_PROCEED,
  /** The server is sending WiFi credentials to the device. */
  PROVIDING_DEVICE_WITH_WIFI_CREDENTIALS,
  /** The device is attempting to connect to WiFi */
  DEVICE_CONNECTING_TO_WIFI,
  /** The server is sending API credentials to the device. */
  PROVIDING_DEVICE_WITH_API_CREDENTIALS,
  /** The server is sending the hostname and related information to the device. */
  PROVIDING_DEVICE_WITH_HOSTNAME_INFO,
  /**
   * The device is attempting to connect to the server via the /hello-world endpoint at {@link
   * ThermostatConnectionTestController#helloWorld()}
   */
  DEVICE_CONNECTING_TO_SERVER,
  /**
   * Setup complete. <b><u>CALL {@link ThermostatSetupController#completeSetup(String)}
   * NEXT!!!</u></b>
   */
  DONE
}
