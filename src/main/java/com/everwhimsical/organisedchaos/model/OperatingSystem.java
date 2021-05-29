package com.everwhimsical.organisedchaos.model;

/**
 * Defined ENUMS to determine and handle different types of operating systems.
 */
public enum OperatingSystem {
  WINDOWS("windows"),
  MAC("mac"),
  LINUX("linux");

  private String operatingSystemName;

  OperatingSystem(String operatingSystemName) {
    this.operatingSystemName = operatingSystemName;
  }

  /**
   * Determine the Operating system type.
   *
   * @return OS object.
   */
  public static OperatingSystem getOperatingSystem() {
    String name = System.getProperties().getProperty("os.name");

    for (OperatingSystem operatingSystemName : values()) {
      if (name.toLowerCase().contains(operatingSystemName.getOperatingSystemType())) {
        return operatingSystemName;
      }
    }
    throw new IllegalArgumentException("Unrecognised operating system name '" + name + "'");
  }

  /**
   * Fetch the name of operating system.
   *
   * @return String value of the OS.
   */
  public String getOperatingSystemType() {
    return operatingSystemName;
  }
}