package com.everwhimsical.organisedchaos.logger;

import com.everwhimsical.organisedchaos.configuration.ChaosConfiguration;
import com.everwhimsical.organisedchaos.configuration.ConfigurationProperty;
import org.slf4j.Logger;

public final class SimpleLogger implements ILoggerConfig {

  public static Logger getLogger() {
    return LoggerFactory.newInstance(SimpleLogger.class);
  }

  @Override
  public LoggerConfig newConfig() {
    LoggerConfig config = new LoggerConfig();
    config.setIgnoreList("org.apache", "org.eclipse.jetty", "org.springframework",
        "io.netty", "org.littleshoot", "net.lightbody");
    config.setLoggerName(getClass().getSimpleName());
    config.setLogFolder(
        ChaosConfiguration.getStringProperty(ConfigurationProperty.OUTPUT_FOLDER));
    config.setLogFileName("run.log");
    return config;
  }
}
