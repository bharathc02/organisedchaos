package com.everwhimsical.organisedchaos.model;

/**
 * UserInterface houses the configuration for a driver object.
 */
public class UserInterface {

  private String operatingSystem;
  private String systemArchitecture;
  private String onDemandService;
  private String onDemandReport;
  /**
   * Emulator, real device
   */
  private String deviceType;
  /**
   * ios, android, windows
   */
  private String platformName;
  private String userAgent;
  private String userAgentVersion;
  private String platformVersion;
  private String deviceName;
  private String appName;
  private String appPath;
  public UserInterface() {
    //TODO check if this is needed since mobile might not use execution machine's OS/Arch
    this.operatingSystem = OperatingSystem.getOperatingSystem().getOperatingSystemType();
    this.systemArchitecture = SystemArchitecture.getSystemArchitecture()
        .getSystemArchitectureType();
  }

  public String getOperatingSystem() {
    return operatingSystem;
  }

  public void setOperatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
  }

  public String getSystemArchitecture() {
    return systemArchitecture;
  }

  public void setSystemArchitecture(String systemArchitecture) {
    this.systemArchitecture = systemArchitecture;
  }

  public String getOnDemandService() {
    return onDemandService;
  }

  public void setOnDemandService(String onDemandService) {
    this.onDemandService = onDemandService;
  }

  public String getOnDemandReport() {
    return onDemandReport;
  }

  public void setOnDemandReport(String onDemandReport) {
    this.onDemandReport = onDemandReport;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getPlatformName() {
    return platformName;
  }

  public void setPlatformName(String platformName) {
    this.platformName = platformName;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public String getUserAgentVersion() {
    return userAgentVersion;
  }

  public void setUserAgentVersion(String userAgentVersion) {
    this.userAgentVersion = userAgentVersion;
  }

  public String getPlatformVersion() {
    return platformVersion;
  }

  public void setPlatformVersion(String platformVersion) {
    this.platformVersion = platformVersion;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppPath() {
    return appPath;
  }

  public void setAppPath(String appPath) {
    this.appPath = appPath;
  }
}