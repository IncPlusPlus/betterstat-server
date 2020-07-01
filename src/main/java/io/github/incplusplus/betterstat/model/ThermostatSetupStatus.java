package io.github.incplusplus.betterstat.model;

public class ThermostatSetupStatus {

  private ThermostatSetupStep currentStep;
  private String ongoingProcess;
  private String exception;

  public ThermostatSetupStatus(
      ThermostatSetupStep currentStep, String ongoingProcess, String exception) {
    this.currentStep = currentStep;
    this.ongoingProcess = ongoingProcess;
    this.exception = exception;
  }

  public ThermostatSetupStep getCurrentStep() {
    return currentStep;
  }

  public void setCurrentStep(ThermostatSetupStep currentStep) {
    this.currentStep = currentStep;
  }

  public String getOngoingProcess() {
    return ongoingProcess;
  }

  public void setOngoingProcess(String ongoingProcess) {
    this.ongoingProcess = ongoingProcess;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }
}
