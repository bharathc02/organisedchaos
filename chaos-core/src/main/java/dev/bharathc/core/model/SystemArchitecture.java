package dev.bharathc.core.model;

import java.util.Arrays;
import java.util.List;

/**
 * Defined ENUMS to determine and handle the system architecture.
 */
public enum SystemArchitecture {
  ARCHITECTURE_64_BIT("64 bit"),
  ARCHITECTURE_32_BIT("32 bit");

  public static final SystemArchitecture defaultSystemArchitecture = ARCHITECTURE_32_BIT;
  private static List<String> architecture64bitNames = Arrays.asList("amd64", "x86_64");
  private String systemArchitectureName;

  SystemArchitecture(String systemArchitectureName) {
    this.systemArchitectureName = systemArchitectureName;
  }

  /**
   * Determine the  system architecture type.
   *
   * @return system architecture object.
   */
  public static SystemArchitecture getSystemArchitecture() {
    final String currentArchitecture = System.getProperties().getProperty("os.arch");
    SystemArchitecture result = defaultSystemArchitecture;
    if (architecture64bitNames.contains(currentArchitecture)) {
      result = ARCHITECTURE_64_BIT;
    }
    return result;
  }

  /**
   * Fetch the type of system architecture.
   *
   * @return String value of the OS.
   */
  public String getSystemArchitectureType() {
    return systemArchitectureName;
  }
}