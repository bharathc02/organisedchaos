package com.everwhimsical.organisedchaos.configuration;

import static com.everwhimsical.organisedchaos.configuration.ConfigurationProperty.Constants.MANDATORY;
import static com.everwhimsical.organisedchaos.configuration.ConfigurationProperty.Constants.OPTIONAL;
import static com.everwhimsical.organisedchaos.configuration.ConfigurationProperty.Constants.READ_ONLY;
import static com.everwhimsical.organisedchaos.configuration.ConfigurationProperty.Constants.SKIP_VALIDATION;
import static com.everwhimsical.organisedchaos.configuration.ConfigurationProperty.Constants.WRITEABLE;

import ch.qos.logback.classic.Level;
import java.io.File;
import org.apache.commons.lang3.StringUtils;

public enum ConfigurationProperty {
    // Core
    RUN_IDENTIFIER("runIdentifier", String.valueOf(System.currentTimeMillis()), MANDATORY,
        READ_ONLY),
    PROJECT_NAME("projectName", "", OPTIONAL, WRITEABLE),
    ENVIRONMENT("environment", "", OPTIONAL, WRITEABLE),
    TEAM("team", "", OPTIONAL),
    USERNAME("user.name", System.getProperty("user.name"), MANDATORY, READ_ONLY),
    LINE_SEPARATOR("log.line.separator", StringUtils.repeat("-", 50), OPTIONAL, READ_ONLY,
        SKIP_VALIDATION),

    // Log
    LOG_LEVEL("log.level", Level.INFO.toString(), OPTIONAL, WRITEABLE, SKIP_VALIDATION),
    LOG_FILE("log.file", "run_" + RUN_IDENTIFIER.value, OPTIONAL, READ_ONLY, SKIP_VALIDATION),

    // Report
    OUTPUT_FOLDER("output.folder", "output" + File.separator + LOG_FILE.getValue() + File.separator,
        MANDATORY, READ_ONLY, SKIP_VALIDATION);

    private final String propertyName;
    private final String value;
    private final boolean mandatory;
    private final boolean readOnly;
    private final boolean skipValidation;

    ConfigurationProperty(String propertyName, String value, boolean mandatory) {
        this(propertyName, value, mandatory, false);
    }

    ConfigurationProperty(String propertyName, String value, boolean mandatory, boolean readOnly) {
        this(propertyName, value, mandatory, readOnly, false);
    }

    ConfigurationProperty(String propertyName, String value, boolean mandatory, boolean readOnly,
        boolean skipValidation) {
        this.propertyName = propertyName;
        this.value = value;
        this.mandatory = mandatory;
        this.readOnly = readOnly;
        this.skipValidation = skipValidation;
    }

    public static ConfigurationProperty parse(String rawText) {
        for (ConfigurationProperty prop : values()) {
            if (prop.getPropertyName().equalsIgnoreCase(rawText)) {
                return prop;
            }
        }
        return null;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getValue() {
        return value;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isSkipValidation() {
        return skipValidation;
    }

    public String getJVMArgumentName() {
        return "chaos_" + getPropertyName();
    }

    interface Constants {

        boolean MANDATORY = true;
        boolean OPTIONAL = false;
        boolean READ_ONLY = true;
        boolean WRITEABLE = false;
        boolean SKIP_VALIDATION = true;
    }
}