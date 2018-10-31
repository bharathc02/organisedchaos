package com.everwhimsical.organisedchaos.logger;

import ch.qos.logback.classic.Level;
import com.everwhimsical.organisedchaos.configuration.ChaosConfiguration;
import com.everwhimsical.organisedchaos.configuration.ConfigurationProperty;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

interface ILoggerConfig {

    default LoggerConfig newConfig(Class<?> clazz) {
        LoggerConfig config = new LoggerConfig();
        List<String> ignoreList = Arrays.asList("org.apache", "org.eclipse.jetty",
            "org.springframework", "io.netty", "org.littleshoot", "net.lightbody");
        ignoreList.addAll(getIgnoreList());
        config.setIgnoreList(ignoreList.toArray(new String[0]));
        config.setLogLevel(Level.INFO.toString());
        config.setLoggerName(clazz.getSimpleName());
        config.setLogFolder(
            ChaosConfiguration.getStringProperty(ConfigurationProperty.OUTPUT_FOLDER));
        config.setLogFileName(clazz.getSimpleName().toLowerCase() + ".log");
        config.setConsolePattern("[ %-5level ] %C{5}.%method\\(\\):%L=> %msg%n");
        config.setFilePattern("%d{HH:mm:ss.SSS} %-5level %C.%method\\(\\):%L=> %msg%n");
        return config;
    }

    default LoggerConfig newConfig() {
        return null;
    }

    default List<String> getIgnoreList() {
        return new LinkedList<>();
    }
}
