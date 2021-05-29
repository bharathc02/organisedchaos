package dev.bharathc.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class WebTest {

  private String webDriverKey = "WebDriver";

  private WebDriver getDriver(ITestContext context) {
    Object attribute = context.getAttribute(webDriverKey);
    if (attribute == null) {
      throw new RuntimeException("WebDriver key not present");
    }
    if (!(attribute instanceof WebDriver)) {
      throw new RuntimeException("WebDriver key has different object");
    }
    return (WebDriver) attribute;
  }

  @AfterMethod
  public void teardown(ITestContext context) {
    WebDriver driver = getDriver(context);
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void testChrome(ITestContext context) {
    ChromeOptions options = new ChromeOptions();
    WebDriver driver = new ChromeDriver(options);
    context.setAttribute(webDriverKey, driver);
    WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
    driver.get("https://the-internet.herokuapp.com/");

    webDriverWait
        .until(ExpectedConditions.elementToBeClickable(By.xpath("//li/a[@href='/inputs']")))
        .click();
  }

  @Test
  public void testFirefox(ITestContext context) {
    FirefoxOptions options = new FirefoxOptions();
    WebDriver driver = new FirefoxDriver(options);
    context.setAttribute(webDriverKey, driver);
    WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
    driver.get("https://the-internet.herokuapp.com/");

    webDriverWait
        .until(ExpectedConditions.elementToBeClickable(By.xpath("//li/a[@href='/inputs']")))
        .click();
  }
}
