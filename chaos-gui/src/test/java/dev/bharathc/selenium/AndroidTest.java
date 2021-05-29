package dev.bharathc.selenium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AndroidTest {

  private AppiumDriver<MobileElement> driver;
  private WebDriverWait webDriverWait;

  @BeforeMethod
  public void setup() throws MalformedURLException {
    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1.0");
    desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
    desiredCapabilities
        .setCapability(MobileCapabilityType.DEVICE_NAME, "Android GoogleAPI Emulator");
    desiredCapabilities
        .setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.apps.maps");
    desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
        "com.google.android.maps.MapsActivity");
    driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), desiredCapabilities);
    webDriverWait = new WebDriverWait(driver, 30);
  }

  @Test
  public void testAndroid() {

  }

  @AfterMethod
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
