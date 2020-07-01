package io.github.incplusplus.betterstat.model;

public class WiFiAvailableNetwork {

  String ssid;
  String securityType;
  double rssi;

  public WiFiAvailableNetwork(String ssid, String securityType, double rssi) {
    this.ssid = ssid;
    this.securityType = securityType;
    this.rssi = rssi;
  }

  public String getSsid() {
    return ssid;
  }

  public void setSsid(String ssid) {
    this.ssid = ssid;
  }

  public String getSecurityType() {
    return securityType;
  }

  public void setSecurityType(String securityType) {
    this.securityType = securityType;
  }

  public double getRssi() {
    return rssi;
  }

  public void setRssi(double rssi) {
    this.rssi = rssi;
  }
}
