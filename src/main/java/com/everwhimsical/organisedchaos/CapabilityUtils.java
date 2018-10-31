package com.everwhimsical.organisedchaos;

import com.everwhimsical.organisedchaos.utility.Commons;
import com.google.common.base.Preconditions;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;

public class CapabilityUtils {

    public static boolean isRemote(Capabilities capabilities) {
        return !Commons.isNull(buildURL(capabilities));
    }

    public static URL buildURL(Capabilities capabilities) {
        Preconditions.checkNotNull(capabilities, "Capabilities cannot be null.");
        Optional<Object> o = Optional.ofNullable(capabilities.getCapability("remoteAddress"));
        URL url = null;
        if (o.isPresent()) {
            String remoteAddressValue = o.get().toString();
            try {
                url = new URL(remoteAddressValue);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException(
                    "Malformed URL set in capabilities. {remoteAddress = '" + remoteAddressValue
                        + "'}");
            }
        }
        return url;
    }

    public static Capabilities buildCapabilities(String browserName) {
        Preconditions.checkNotNull(browserName, "Browser Name cannot be null");
        Capabilities capabilities;
        switch (BrowserVariants.parse(browserName)) {
            case FIREFOX:
                capabilities = new FirefoxOptions();
                break;
            case INTERNET_EXPLORER:
                capabilities = new InternetExplorerOptions();
                break;
            case SAFARI:
                capabilities = new SafariOptions();
                break;
            case MICROSOFT_EDGE:
                capabilities = new EdgeOptions();
                break;
            case OPERA:
                capabilities = new OperaOptions();
                break;
            case CHROME:
                capabilities = getChromeOptions(false);
                break;
            case CHROME_ANDROID:
                capabilities = getChromeAndroidOptions();
                break;
            case CHROME_HEADLESS:
            default:
                capabilities = getChromeOptions(true);
                break;
        }
        return capabilities;
    }


    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions chrome = new ChromeOptions();
        Map<String, String> chromePreferences = new HashMap<>();
        chromePreferences.put("profile.password_manager_enabled", "false");
        chrome.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
        chrome.setCapability("chrome.prefs", chromePreferences);
        chrome.setHeadless(headless);
        return chrome;
    }

    private static DesiredCapabilities getChromeAndroidOptions() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android");
        capabilities.setCapability("remoteAddress", "http://127.0.0.1:4723/wd/hub");
        return capabilities;
    }

    public static boolean isBrowser(Capabilities capabilities) {
        return Optional.ofNullable(capabilities)
            .map(Capabilities::getBrowserName)
            .filter(Commons::isNotNullAndNotEmpty)
            .isPresent();
    }

    public static String getPlatform(Capabilities capabilities) {
        return Optional.ofNullable(capabilities)
            .map(entry -> {
                if (entry.getCapability(MobileCapabilityType.PLATFORM_NAME) != null) {
                    return entry.getCapability(MobileCapabilityType.PLATFORM_NAME);
                } else if (entry.getCapability(MobileCapabilityType.PLATFORM) != null) {
                    return entry.getCapability(MobileCapabilityType.PLATFORM);
                }
                return entry.getCapability("platformBackup");
            })
            .orElse("").toString();
    }

    public static boolean isAndroid(Capabilities capabilities) {
        return getPlatform(capabilities).equalsIgnoreCase("Android");
    }

    public static boolean isIOS(Capabilities capabilities) {
        return getPlatform(capabilities).equalsIgnoreCase("iOS");
    }

    public static void updateLoggingCapabilities(Capabilities capabilities) {
        if (!Boolean.valueOf(System.getProperty("browserLogs", "true"))) {
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            System.setProperty("webdriver.chrome.args", "--disable-logging");
            System.setProperty("webdriver.chrome.silentOutput", "true");
        }
        if (capabilities.getCapability(CapabilityType.LOGGING_PREFS) == null) {
            LoggingPreferences pref = new LoggingPreferences();
            pref.enable(LogType.BROWSER, Level.OFF);
            pref.enable(LogType.CLIENT, Level.OFF);
            pref.enable(LogType.DRIVER, Level.OFF);
            pref.enable(LogType.PERFORMANCE, Level.OFF);
            pref.enable(LogType.PROFILER, Level.OFF);
            pref.enable(LogType.SERVER, Level.OFF);
            DesiredCapabilities loggingCapabilities = new DesiredCapabilities();
            loggingCapabilities.setCapability(CapabilityType.LOGGING_PREFS, pref);
            capabilities.merge(loggingCapabilities);
        }
    }
}
