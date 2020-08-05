package io.github.incplusplus.betterstat.service;

import static io.github.incplusplus.betterstat.model.ThermostatSetupStep.PROVIDING_DEVICE_WITH_API_CREDENTIALS;
import static io.github.incplusplus.betterstat.model.ThermostatSetupStep.PROVIDING_DEVICE_WITH_HOSTNAME_INFO;
import static io.github.incplusplus.betterstat.model.ThermostatSetupStep.PROVIDING_DEVICE_WITH_WIFI_CREDENTIALS;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.PROMPT_API_PASSWORD;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.PROMPT_API_USERNAME;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.PROMPT_SERVER_HOSTNAME;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.PROMPT_SERVER_HOSTNAME_IS_IP;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.PROMPT_SERVER_IS_SECURE;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.PROMPT_SERVER_PORT;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.PROMPT_WIFI_PASSWORD;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.PROMPT_WIFI_SSID;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.STATUS_CONNECTING_TO_SERVER;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.STATUS_RADIO_STATUS;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.STATUS_SETUP_COMPLETE;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.STATUS_SETUP_FAILED;
import static io.github.incplusplus.betterstat.utils.SetupConstants.DeviceStrings.STATUS_WAITING_FOR_SETUP;
import static io.github.incplusplus.betterstat.utils.SetupConstants.ServerStrings.SINGLE_BYTE_STRING;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import io.github.incplusplus.betterstat.model.ThermostatSetupStatus;
import io.github.incplusplus.betterstat.model.ThermostatSetupStep;
import io.github.incplusplus.betterstat.persistence.model.Thermostat;
import io.github.incplusplus.betterstat.persistence.model.ThermostatApiUser;
import io.github.incplusplus.betterstat.persistence.repositories.ThermostatApiUserRepository;
import io.github.incplusplus.betterstat.persistence.repositories.ThermostatRepository;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ThermostatSetupServiceImpl implements ThermostatSetupService {

  private static final Logger log = LoggerFactory.getLogger(ThermostatSetupServiceImpl.class);
  private final ThermostatRepository thermostatRepository;
  private final ThermostatApiUserRepository thermostatApiUserRepository;
  private final PasswordEncoder passwordEncoder;
  private final Map<String, ThermostatSetupHelper> setupHelpers = new HashMap<>();

  @Autowired
  public ThermostatSetupServiceImpl(
      ThermostatRepository thermostatRepository,
      ThermostatApiUserRepository thermostatApiUserRepository,
      PasswordEncoder passwordEncoder) {
    this.thermostatRepository = thermostatRepository;
    this.thermostatApiUserRepository = thermostatApiUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public ThermostatSetupStatus getCurrentSetupStatus(String thermostatId) {
    if (setupHelpers.get(thermostatId) == null) {
      return null;
    }
    return setupHelpers.get(thermostatId).getSetupStatus();
  }

  @Override
  public void initSetup(String thermostatId) throws ObjectNotFoundException {
    // Check all the existing
    // Allow us to re-init a failed setup, otherwise, only one setup process may occur at a time
    if (nonNull(setupHelpers.get(thermostatId))) {
      ThermostatApiUser apiCredentials =
          setupHelpers.get(thermostatId).getApiCredentialsPWEncoded();
      if (nonNull(apiCredentials))
      // Delete credentials from the previous setup attempt
      {
        thermostatApiUserRepository.delete(apiCredentials);
      }
      if (nonNull(setupHelpers.get(thermostatId).getSerialPort())) {
        if (setupHelpers.get(thermostatId).getSerialPort().isOpen()) {
          SerialPort port = setupHelpers.get(thermostatId).getSerialPort();
          port.closePort();
          port.removeDataListener();
        }
      }
    } else {
      ensureOnlyOneSetupInProgress();
    }
    setupHelpers.put(thermostatId, new ThermostatSetupHelper(thermostatId));
    // Don't forget that this could be run on a thermostat which has already been set up!
    // You should probably prompt the user in the app to make sure this is what they intended.
    Optional<Thermostat> thermostatOptional = thermostatRepository.findById(thermostatId);
    if (thermostatOptional.isEmpty()) {
      throw new ObjectNotFoundException(thermostatId, Thermostat.class);
    }
    Thermostat thermostat = thermostatOptional.get();
    ThermostatApiUser apiCredentials = createThermostatApiUser(thermostat);
    setupHelpers.get(thermostatId).setApiCredentials(apiCredentials);
    /*
     * We kept the password in the output of createThermostatApiUser() so that the Arduino had the real password.
     * Now we encode it for safe keeping.
     */
    ThermostatApiUser apiCredentialsPWEncoded =
        new ThermostatApiUser(
            apiCredentials.getThermostat(),
            apiCredentials.getUsername(),
            passwordEncoder.encode(apiCredentials.getPassword()));
    setupHelpers
        .get(thermostatId)
        .setApiCredentialsPWEncoded(thermostatApiUserRepository.save(apiCredentialsPWEncoded));
    /*
     TODO: Add a warning system that holds a backlog of warnings that the user should be able to
      view in the app. An example of an important warning would be if a thermostat is trying to
      report its status but isn't set up yet as this might be noteworthy.
    */
    thermostat.setSetUp(false);
    thermostatRepository.save(thermostat);
    setupHelpers
        .get(thermostatId)
        .setSetupStatus(
            new ThermostatSetupStatus(ThermostatSetupStep.NEED_THERMOSTAT_CONNECTION, "", ""));
  }

  @Override
  public boolean beginSetup(String thermostatId) throws ObjectNotFoundException {
    ensureSetupHelperReady(thermostatId);
    return setupHelpers.get(thermostatId).beginSetup();
  }

  /**
   * Makes sure that we have all the info we'll need to send to the device beforehand.
   *
   * @param thermostatId the id of the thermostat/helper
   */
  private void ensureSetupHelperReady(String thermostatId) {
    ThermostatSetupHelper helper = setupHelpers.get(thermostatId);
    // TODO: Make a proper setup-related exception for all of these
    if (isNull(helper)) {
      throw new RuntimeException("Call /init first");
    }
    if (isNull(helper.getWifiSsid())) {
      throw new RuntimeException("Missing SSID");
    }
    if (isNull(helper.getWifiPassword())) {
      throw new RuntimeException("Missing password");
    }
    if (isNull(helper.getHostnameIsAnIP())) {
      throw new RuntimeException("Missing boolean: hostnameIsAnIP");
    }
    if (isNull(helper.getIsSecure())) {
      throw new RuntimeException("Missing boolean: isSecure");
    }
    if (helper.getHostPort() == 0) {
      throw new RuntimeException("Missing hostPort");
    }
    if (isNull(helper.getServerHostname())) {
      throw new RuntimeException("Missing server hostname");
    }
    if (isNull(helper.getApiCredentials())) {
      throw new RuntimeException("Missing API credentials");
    }
  }

  @Override
  public List<DescriptivePortNameSystemPortNamePair> getPorts() {
    return Arrays.stream(SerialPort.getCommPorts())
        .map(
            serialPort ->
                new DescriptivePortNameSystemPortNamePair(
                    serialPort.getDescriptivePortName(), serialPort.getSystemPortName()))
        .collect(Collectors.toList());
  }

  @Override
  public boolean connectToSystemPort(String systemPortName, String thermostatId) {
    SerialPort port = SerialPort.getCommPort(systemPortName);
    port.addDataListener(new MessageListener(setupHelpers.get(thermostatId)));
    port.setBaudRate(115200);
    boolean portOpenSuccess = port.openPort();
    if (!portOpenSuccess) {
      if (!(nonNull(setupHelpers.get(thermostatId).getSerialPort())
          && setupHelpers.get(thermostatId).getSerialPort().isOpen())) {
        /*
         * If the setup helper already has a port and it's open, that's probably why we couldn't open this port.
         * If those conditions aren't present, then raise the alarm because we should be able to open the port.
         */
        setupHelpers
            .get(thermostatId)
            .setSetupStatus(
                new ThermostatSetupStatus(ThermostatSetupStep.ERROR, "", "Couldn't open port."));
      }
      return false;
    }
    setupHelpers.get(thermostatId).setSerialPort(port);
    // Write a single byte to let the device know we're here
    int writeResult =
        port.writeBytes(SINGLE_BYTE_STRING.getBytes(), SINGLE_BYTE_STRING.getBytes().length);
    setupHelpers
        .get(thermostatId)
        .setSetupStatus(
            new ThermostatSetupStatus(ThermostatSetupStep.WAITING_FOR_DEVICE_RESPONSE, "", ""));
    if (writeResult == -1) {
      setupHelpers
          .get(thermostatId)
          .setSetupStatus(
              new ThermostatSetupStatus(
                  ThermostatSetupStep.ERROR, "", "There was an error writing to the device."));
      return false;
    }
    return true;
  }

  @Override
  public void setSsid(String thermostatId, String ssid) {
    setupHelpers.get(thermostatId).setWifiSsid(ssid);
  }

  @Override
  public void setPassword(String thermostatId, String password) {
    setupHelpers.get(thermostatId).setWifiPassword(password);
  }

  @Override
  public void setServerHostname(
      String thermostatId,
      String serverHostname,
      boolean hostnameIsAnIP,
      boolean isSecure,
      int port) {
    ThermostatSetupHelper helper = setupHelpers.get(thermostatId);
    helper.setServerHostname(serverHostname);
    helper.setHostnameIsAnIP(hostnameIsAnIP);
    helper.setIsSecure(isSecure);
    helper.setHostPort(port);
  }

  @Override
  public void completeSetup(String thermostatId) throws ObjectNotFoundException {
    if (isNull(setupHelpers.get(thermostatId))) {
      log.warn(
          "A client tried to complete a setup for a Thermostat with id "
              + thermostatId
              + " but there was no setup in progress.");
      return;
    }
    if (setupHelpers.get(thermostatId).getSetupStatus().getCurrentStep()
        != ThermostatSetupStep.DONE)
    // TODO: Throw a proper exception
    {
      throw new RuntimeException(
          "Setup process incomplete. Start over or supply the necessary information to complete the in-progress setup.");
    }
    if (setupHelpers.get(thermostatId).getSerialPort().isOpen()) {
      setupHelpers.get(thermostatId).getSerialPort().closePort();
      setupHelpers.get(thermostatId).getSerialPort().removeDataListener();
    }
    setupHelpers.remove(thermostatId);
    Optional<Thermostat> thermostatOptional = thermostatRepository.findById(thermostatId);
    if (thermostatOptional.isEmpty()) {
      throw new ObjectNotFoundException(thermostatId, Thermostat.class);
    }
    Thermostat thermostat = thermostatOptional.get();
    thermostat.setSetUp(true);
    thermostatRepository.save(thermostat);
  }

  /**
   * This boolean is used to indicate if any devices are being provisioned. If there are any, then
   * the current setup must be completed or cancelled.
   */
  private boolean isSetupInProgress() {
    /*
    Only one provisioning process may be run at a time. The reason that they're kept in a collection
    is just to keep track of the ID of the thermostat being configured.
     */
    return !setupHelpers.entrySet().isEmpty();
  }

  private void ensureOnlyOneSetupInProgress() {
    if (isSetupInProgress())
    // TODO: Make a proper exception
    {
      throw new RuntimeException("Setup already in progress");
    }
  }

  /**
   * Creates a new {@link ThermostatApiUser} which is persisted separately from the Thermostat
   * class. A ThermostatApiUser contains a {@link
   * org.springframework.data.mongodb.core.mapping.DBRef} to the thermostat it belongs to as well as
   * credentials for the thermostat to provide when accessing API endpoints.
   *
   * <p>The username and password are both 32 bytes long. This allows the credentials to be a known
   * length on the device (64 bytes combined).
   *
   * @param thermostat the thermostat to create credentials for
   * @return a ThermostatApiUser with new credentials and a reference to the provided Thermostat
   */
  private ThermostatApiUser createThermostatApiUser(Thermostat thermostat) {
    SecureRandom sr = new SecureRandom();
    byte[] un = new byte[24];
    byte[] pwd = new byte[24];
    sr.nextBytes(un);
    sr.nextBytes(pwd);
    Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    /*
     * Normally, the bytes produced by SecureRandom.nextBytes can contain negative numbers.
     * Running it through this encoder makes everything valid again which makes me happy.
     * The reason we encode 24 bytes is because in Base64, each 3 bytes of unencoded data become 4 bytes of encoded data.
     * The final string lengths should each be 32.
     */
    String username = encoder.encodeToString(un);
    String password = encoder.encodeToString(pwd);
    if (username.length() != 32 || password.length() != 32) {
      throw new IllegalStateException(
          "Expected username and password created by the server to be 32 characters long but the username was "
              + username.length()
              + " and the password was "
              + password.length()
              + ".");
    }
    return new ThermostatApiUser(thermostat, username, password);
  }

  public static class MessageListener implements SerialPortMessageListener {

    private final ThermostatSetupHelper setupHelper;

    public MessageListener(ThermostatSetupHelper setupHelper) {
      this.setupHelper = setupHelper;
    }

    @Override
    public int getListeningEvents() {
      return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
      String eventString = new String(event.getReceivedData()).strip();
      /*
       * TODO: It would be nice for this to be a bit less fragile. To do that, we should prefix any setup strings with some
       *  special ASCII character so that we know it's related to the setup process. We should also prefix any debug output
       *  that the Arduino sends over serial with some other ASCII character (or possibly just "[DEBUG]").
       *  The reason we should prefix these instead of just prefixing errors is because we aren't always in control of
       *  error output. For example, the ESP32 will sometimes print to serial if it has been restarted despite us
       *  never doing that in-code.
       */
      System.out.println("Received the following message: '" + eventString + "'");
      /*
       * This is separate from the other (would-be switch) if/else chain because these START WITH a certain string.
       * We need to check for it and then extract the payload. To avoid having this run through the switch, the switch
       * is contained in an else block in the case that there is no string to payload-check.
       */
      if (eventString.startsWith(STATUS_SETUP_FAILED)) {
        String reasonEncoded = substringAfterLast(eventString, STATUS_SETUP_FAILED);
        /*
         * We don't always know what the reason parameter will contain. If it contains newlines,
         * the server's reading methods will stop reading once that is seen. To smuggle those through,
         * a base64 encoded string is passed to the server.
         */
        String reason = new String(Base64.getDecoder().decode(reasonEncoded));
        setupHelper.setSetupStatus(
            new ThermostatSetupStatus(ThermostatSetupStep.ERROR, "", reason));
      } else if (eventString.startsWith(STATUS_RADIO_STATUS)) {
        setupHelper.setSetupStatus(
            new ThermostatSetupStatus(
                ThermostatSetupStep.DEVICE_CONNECTING_TO_WIFI,
                substringAfterLast(eventString, STATUS_RADIO_STATUS),
                ""));
      } else if (eventString.startsWith(STATUS_CONNECTING_TO_SERVER)) {
        setupHelper.setSetupStatus(
            new ThermostatSetupStatus(
                ThermostatSetupStep.DEVICE_CONNECTING_TO_SERVER,
                substringAfterLast(eventString, STATUS_CONNECTING_TO_SERVER),
                ""));
      } else { // TODO: Replace with new-style switch once fmt-maven-plugin stops choking on it
        if (STATUS_WAITING_FOR_SETUP.equals(eventString)) {
          setupHelper.setSetupStatus(
              new ThermostatSetupStatus(ThermostatSetupStep.SETUP_READY_TO_PROCEED, "", ""));
        } else if (PROMPT_WIFI_SSID.equals(eventString)) {
          setupHelper.setSetupStatus(
              new ThermostatSetupStatus(PROVIDING_DEVICE_WITH_WIFI_CREDENTIALS, "", ""));
          setupHelper.enterWifiSsid();
        } else if (PROMPT_WIFI_PASSWORD.equals(eventString)) {
          setupHelper.enterWifiPassword();
        } else if (PROMPT_API_USERNAME.equals(eventString)) {
          setupHelper.setSetupStatus(
              new ThermostatSetupStatus(PROVIDING_DEVICE_WITH_API_CREDENTIALS, "", ""));
          setupHelper.enterApiUsername();
        } else if (PROMPT_API_PASSWORD.equals(eventString)) {
          setupHelper.enterApiPassword();
        } else if (PROMPT_SERVER_HOSTNAME_IS_IP.equals(eventString)) {
          setupHelper.setSetupStatus(
              new ThermostatSetupStatus(PROVIDING_DEVICE_WITH_HOSTNAME_INFO, "", ""));
          setupHelper.enterHostnameIsAnIP();
        } else if (PROMPT_SERVER_IS_SECURE.equals(eventString)) {
          setupHelper.enterIsSecure();
        } else if (PROMPT_SERVER_PORT.equals(eventString)) {
          setupHelper.enterHostPort();
        } else if (PROMPT_SERVER_HOSTNAME.equals(eventString)) {
          setupHelper.enterHostname();
        } else if (STATUS_SETUP_COMPLETE.equals(eventString)) {
          setupHelper.setSetupStatus(new ThermostatSetupStatus(ThermostatSetupStep.DONE, "", ""));
          setupHelper.getSerialPort().closePort();
          setupHelper.getSerialPort().removeDataListener();
        }
      }
    }

    @Override
    public byte[] getMessageDelimiter() {
      return "\n".getBytes();
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
      return true;
    }
  }

  public static class DescriptivePortNameSystemPortNamePair {

    private String descriptivePortName;
    private String systemPortName;

    public DescriptivePortNameSystemPortNamePair(
        String descriptivePortName, String systemPortName) {
      this.descriptivePortName = descriptivePortName;
      this.systemPortName = systemPortName;
    }

    public String getDescriptivePortName() {
      return descriptivePortName;
    }

    public void setDescriptivePortName(String descriptivePortName) {
      this.descriptivePortName = descriptivePortName;
    }

    public String getSystemPortName() {
      return systemPortName;
    }

    public void setSystemPortName(String systemPortName) {
      this.systemPortName = systemPortName;
    }
  }

  public class ThermostatSetupHelper {

    private final AtomicReference<ThermostatSetupStatus> setupStatus;
    private final String thermostatId;
    private String wifiSsid;
    private String wifiPassword;
    private SerialPort serialPort;
    private Boolean hostnameIsAnIP;
    private Boolean isSecure;
    private int hostPort;
    private String serverHostname;
    private ThermostatApiUser apiCredentials;
    private ThermostatApiUser apiCredentialsPWEncoded;

    public ThermostatSetupHelper(String thermostatId) {
      this.thermostatId = thermostatId;
      setupStatus =
          new AtomicReference<>(
              new ThermostatSetupStatus(ThermostatSetupStep.NEED_THERMOSTAT_CONNECTION, "", ""));
    }

    public boolean beginSetup() {
      return writeString(SINGLE_BYTE_STRING);
    }

    public ThermostatSetupStatus getSetupStatus() {
      return setupStatus.get();
    }

    public void setSetupStatus(ThermostatSetupStatus setupStatus) {
      this.setupStatus.set(setupStatus);
    }

    public String getWifiSsid() {
      return wifiSsid;
    }

    public void setWifiSsid(String wifiSsid) {
      this.wifiSsid = wifiSsid;
    }

    public String getWifiPassword() {
      return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
      this.wifiPassword = wifiPassword;
    }

    public SerialPort getSerialPort() {
      return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
      this.serialPort = serialPort;
    }

    public Object getServerHostname() {
      return serverHostname;
    }

    public void setServerHostname(String serverHostname) {
      this.serverHostname = serverHostname;
    }

    private boolean writeString(String s) {
      System.out.println("Writing '" + s + "'");
      int writeResult = serialPort.writeBytes(s.getBytes(), s.getBytes().length);
      if (writeResult == -1) {
        setupHelpers
            .get(thermostatId)
            .setSetupStatus(
                new ThermostatSetupStatus(
                    ThermostatSetupStep.ERROR, "", "There was an error writing to the device."));
        return false;
      }
      return true;
    }

    private void removePastCredentials(Thermostat thermostat) {}

    public void enterWifiSsid() {
      writeString(wifiSsid + "\n");
    }

    public void enterWifiPassword() {
      writeString(wifiPassword + "\n");
    }

    public void enterApiUsername() {
      writeString(apiCredentials.getUsername() + "\n");
    }

    public void enterApiPassword() {
      writeString(apiCredentials.getPassword() + "\n");
    }

    public void enterHostname() {
      writeString(serverHostname + "\n");
    }

    public void enterHostnameIsAnIP() {
      writeString(hostnameIsAnIP + "\n");
    }

    public void enterIsSecure() {
      writeString(isSecure + "\n");
    }

    public void enterHostPort() {
      writeString(hostPort + "\n");
    }

    public ThermostatApiUser getApiCredentials() {
      return apiCredentials;
    }

    public void setApiCredentials(ThermostatApiUser apiCredentials) {
      this.apiCredentials = apiCredentials;
    }

    public Boolean getHostnameIsAnIP() {
      return hostnameIsAnIP;
    }

    public void setHostnameIsAnIP(Boolean hostnameIsAnIP) {
      this.hostnameIsAnIP = hostnameIsAnIP;
    }

    public Boolean getIsSecure() {
      return isSecure;
    }

    public void setIsSecure(Boolean isSecure) {
      this.isSecure = isSecure;
    }

    public int getHostPort() {
      return hostPort;
    }

    public void setHostPort(int hostPort) {
      this.hostPort = hostPort;
    }

    public ThermostatApiUser getApiCredentialsPWEncoded() {
      return apiCredentialsPWEncoded;
    }

    public void setApiCredentialsPWEncoded(ThermostatApiUser apiCredentialsPWEncoded) {
      this.apiCredentialsPWEncoded = apiCredentialsPWEncoded;
    }
  }
}
