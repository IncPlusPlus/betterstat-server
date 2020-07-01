package io.github.incplusplus.betterstat.web.util;

public class GenericResponseBuilder {

  private String message;
  private Object additionalDetails;
  private StackTraceElement[] stackTrace;

  public GenericResponseBuilder setMessage(String message) {
    this.message = message;
    return this;
  }

  public GenericResponseBuilder setAdditionalDetails(Object additionalDetails) {
    this.additionalDetails = additionalDetails;
    return this;
  }

  public GenericResponseBuilder setStackTrace(StackTraceElement[] stackTrace) {
    this.stackTrace = stackTrace;
    return this;
  }

  public GenericResponse createGenericResponse() {
    return new GenericResponse(message, additionalDetails, stackTrace);
  }
}
