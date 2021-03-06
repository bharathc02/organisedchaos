package dev.bharathc.core.model;

import java.io.File;
import java.time.ZonedDateTime;

public interface FileMetaData {

  File getFile();

  String getDescription();

  ZonedDateTime getCapturedDateTime();

  Status getStatus();
}
