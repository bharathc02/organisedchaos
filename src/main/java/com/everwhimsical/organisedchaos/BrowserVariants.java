package com.everwhimsical.organisedchaos;

import java.util.Arrays;

public enum BrowserVariants {
  FIREFOX("firefox"),
  CHROME("chrome"),
  INTERNET_EXPLORER("internet explorer"),
  MICROSOFT_EDGE("MicrosoftEdge"),
  HTMLUNIT("htmlunit"),
  SAFARI("safari"),
  OPERA("opera"),
  CHROME_ANDROID("chrome_android"),
  CHROME_HEADLESS("chrome_headless");

  private final String browserName;

  BrowserVariants(String browserName) {
    this.browserName = browserName;
  }

  public static BrowserVariants parse(final String browserName) {
    return Arrays.stream(BrowserVariants.values())
        .filter(browser -> browser.getBrowserName().equalsIgnoreCase(browserName))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException("Invalid browser name passed. " + browserName));
  }

  public String getBrowserName() {
    return browserName;
  }
}
