package io.github.incplusplus.betterstat.web.exception;

public class ObjectNotFoundException extends Exception {

  private final Class<?> instanceType;
  private final String id;

  public ObjectNotFoundException(String id, Class<?> aClass) {
    this.id = id;
    this.instanceType = aClass;
  }

  public String getId() {
    return id;
  }

  public Class<?> getInstanceType() {
    return instanceType;
  }
}
