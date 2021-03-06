package dev.bharathc.core.model;

public class Bug {

  private String id;
  private String message;
  private String stackTrace;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  @Override
  public String toString() {
    return "Bug{" +
        "id='" + id + '\'' +
        ", message='" + message + '\'' +
        ", stackTrace='" + stackTrace + '\'' +
        '}';
  }
}
