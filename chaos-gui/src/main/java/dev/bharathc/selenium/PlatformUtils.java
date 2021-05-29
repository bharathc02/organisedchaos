package dev.bharathc.selenium;

import java.util.Optional;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class PlatformUtils {

  public static boolean isAndroid(WebDriver driver) {
    return isPlatform(driver, "Android");
  }

  public static boolean isIOS(WebDriver driver) {
    return isPlatform(driver, "iOS");
  }

  public static boolean isAndroid(WebDriver driver, String actualPlatform) {
    return isPlatform(driver, actualPlatform);
  }

  public static boolean isIOS(WebDriver driver, String actualPlatform) {
    return isPlatform(driver, actualPlatform);
  }

  public static boolean isPlatform(WebDriver driver, String actualPlatform) {
    Capabilities capabilities = null;
    if (driver instanceof EventFiringWebDriver && ((EventFiringWebDriver) driver)
        .getWrappedDriver() instanceof HasCapabilities) {
      capabilities = ((HasCapabilities) ((EventFiringWebDriver) driver).getWrappedDriver())
          .getCapabilities();
    } else if (driver instanceof HasCapabilities) {
      capabilities = ((HasCapabilities) driver).getCapabilities();
    }
    String platform = Optional.ofNullable(capabilities)
        .map(entry -> {
          if (entry.getCapability("platformBackup") != null) {
            return entry.getCapability("platformBackup");
          } else if (entry.getCapability("platformName") != null) {
            return entry.getCapability("platformName");
          }
          return entry.getCapability("platform");
        })
        .orElse("").toString();
    return platform.equalsIgnoreCase(actualPlatform);
  }
}
