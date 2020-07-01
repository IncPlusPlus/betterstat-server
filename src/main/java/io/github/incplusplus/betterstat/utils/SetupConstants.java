package io.github.incplusplus.betterstat.utils;

public class SetupConstants {

  public static class DeviceStrings {

    public static final String STATUS_WAITING_FOR_SETUP = "WAITING_FOR_SETUP";
    public static final String STATUS_SETUP_COMPLETE = "SETUP_COMPLETE";
    public static final String STATUS_SETUP_FAILED = "SETUP_FAILED";
    public static final String STATUS_RADIO_STATUS = "Radio status: ";
    public static final String STATUS_CONNECTING_TO_SERVER = "Connecting to server: ";
    public static final String PROMPT_WIFI_SSID = "WiFi SSID:";
    public static final String PROMPT_WIFI_PASSWORD = "WiFi Password:";
    public static final String PROMPT_API_USERNAME = "API username:";
    public static final String PROMPT_API_PASSWORD = "API password:";
    public static final String PROMPT_SERVER_HOSTNAME_IS_IP = "Hostname is an IP:";
    public static final String PROMPT_SERVER_IS_SECURE = "Is secure:";
    public static final String PROMPT_SERVER_PORT = "Port:";
    public static final String PROMPT_SERVER_HOSTNAME = "Hostname:";
  }

  public static class ServerStrings {

    /**
     * This string is used to both pause the startup process which causes a thermostat to wait, as
     * well as to proceed with the process.
     */
    public static final String SINGLE_BYTE_STRING = "S";
  }
}
