package dev.bharathc.core.configuration;

import static com.google.common.base.Preconditions.checkNotNull;

import dev.bharathc.core.utility.Commons;
import org.apache.commons.configuration2.XMLConfiguration;

public class ChaosConfiguration {

  private static final XMLConfiguration configuration = new XMLConfiguration();
  private static boolean initialised;

  static {
    for (ConfigurationProperty property : ConfigurationProperty.values()) {
      configuration.addProperty(property.getPropertyName(), property.getValue());
    }
  }

  public static synchronized void initGlobalConfig() {
    if (!initialised) {
      updateConfigUsingJVM(configuration);
      initialised = true;
    }
  }

  public static boolean getBooleanProperty(ConfigurationProperty property) {
    if (property.isSkipValidation()) {
      return getConfiguration().getBoolean(property.getPropertyName());
    }
    return getConfiguration().getBoolean(property.getPropertyName());
  }

  public static int getIntProperty(ConfigurationProperty property) {
    if (property.isSkipValidation()) {
      return getConfiguration().getInt(property.getPropertyName());
    }
    return getConfiguration().getInt(property.getPropertyName());
  }

  public static String getStringProperty(ConfigurationProperty property) {
    checkNotNull(property, "The ConfigurationProperty cannot be null");
    if (property.isSkipValidation()) {
      return getConfiguration().getString(property.getPropertyName()).trim();
    }
    return getConfiguration().getString(property.getPropertyName()).trim();
  }

  private static XMLConfiguration getConfiguration() {
    return configuration;
  }

  private static synchronized void updateConfigUsingJVM(XMLConfiguration configuration) {
    for (ConfigurationProperty property : ConfigurationProperty.values()) {
      if (property.isReadOnly()) {
        //We are not going to be updating ourselves with READONLY properties
        continue;
      }
      String value = System.getProperty(property.getPropertyName(), "");
      if (!Commons.isNullOrEmpty(value)) {
        configuration.setProperty(property.getPropertyName(), value);
      }
      //finally query the JVM for the new Slingshot configuration parameters and then update our
      //configuration accordingly. In the near future this will be all that is required.
      value = System.getProperty(property.getJVMArgumentName(), "");
      if (!Commons.isNullOrEmpty(value)) {
        configuration.setProperty(property.getPropertyName(), value);
      }
    }
  }
}
