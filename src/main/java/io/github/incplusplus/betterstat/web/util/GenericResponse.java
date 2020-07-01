package io.github.incplusplus.betterstat.web.util;

public class GenericResponse {
  private String message;
  private Object additionalDetails;
  private StackTraceElement[] stackTrace;

  public GenericResponse(String message, Object additionalDetails, StackTraceElement[] stackTrace) {
    this.message = message;
    this.additionalDetails = additionalDetails;
    this.stackTrace = stackTrace;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getAdditionalDetails() {
    return additionalDetails;
  }

  public void setAdditionalDetails(Object additionalDetails) {
    this.additionalDetails = additionalDetails;
  }

  public StackTraceElement[] getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(StackTraceElement[] stackTrace) {
    this.stackTrace = stackTrace;
  }
}
