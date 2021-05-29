package dev.bharathc.core.model;

import java.io.File;
import java.time.ZonedDateTime;

public class ResourceMetaData implements FileMetaData {

  private File file;
  private String description;
  private ZonedDateTime capturedDateTime;
  private Status status;

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ZonedDateTime getCapturedDateTime() {
    return capturedDateTime;
  }

  public void setCapturedDateTime(ZonedDateTime capturedDateTime) {
    this.capturedDateTime = capturedDateTime;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
