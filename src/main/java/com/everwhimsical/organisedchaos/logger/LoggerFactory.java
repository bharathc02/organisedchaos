package com.everwhimsical.organisedchaos.logger;

import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.apache.commons.io.FilenameUtils.getExtension;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TriggeringPolicy;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.FileSize;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class LoggerFactory {

  private static final Object myLock = new Object();
  private static Map<String, Logger> loggerMap = new ConcurrentHashMap<>();

  private static Logger newLogger(LoggerConfig config) {
    config.validateInternals();
    LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
    Logger logger = loggerContext.getLogger(config.getLoggerName());
    logger.setAdditive(false);
    for (String each : config.getIgnoreList()) {
      ((Logger) org.slf4j.LoggerFactory.getLogger(each)).setLevel(Level.OFF);
    }
    ConsoleAppender<ILoggingEvent> consoleAppender = newConsoleAppender(loggerContext, logger,
        config);
    if (logger.getAppender(consoleAppender.getName()) == null) {
      logger.addAppender(consoleAppender);
    }
    FileAppender<ILoggingEvent> fileAppender = newFileAppender(loggerContext, logger, config);
    if (logger.getAppender(fileAppender.getName()) == null) {
      logger.addAppender(fileAppender);
    }
    return logger;
  }

  private static FileAppender<ILoggingEvent> newFileAppender(LoggerContext context, Logger logger,
      LoggerConfig config) {
    String file = config.getLogFileName();
    String pattern = config.getFilePattern() == null
        ? "%d{HH:mm:ss.SSS} %-5level %C.%method\\(\\):%L=> %msg%n" : config.getFilePattern();
    RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
    appender.setName("file_" + logger.getName());
    appender.setFile(normalizeFolderName(config.getLogFolder()) + file);
    appender.setContext(context);
    appender.setAppend(false);
    appender.setLayout(getLayout(pattern, context));
    appender.setRollingPolicy(getRollingPolicy(context, config, appender));
    appender.setTriggeringPolicy(getTriggerPolicy(context, config.getFileSize()));
    appender.start();
    return appender;
  }

  private static ConsoleAppender<ILoggingEvent> newConsoleAppender(LoggerContext context,
      Logger logger, LoggerConfig config) {
    String pattern =
        config.getConsolePattern() == null ? "[ %-5level ] %C{5}.%method\\(\\):%L=> %msg%n"
            : config.getConsolePattern();
    ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
    appender.setName("console_" + logger.getName());
    appender.setContext(context);
    appender.setLayout(getLayout(pattern, context));
    appender.addFilter(new LogFilter());
    appender.start();
    return appender;
  }

  private static TriggeringPolicy<ILoggingEvent> getTriggerPolicy(LoggerContext context,
      String size) {
    SizeBasedTriggeringPolicy<ILoggingEvent> policy = new SizeBasedTriggeringPolicy<>();
    policy.setMaxFileSize(FileSize.valueOf(size));
    policy.setContext(context);
    policy.start();
    return policy;
  }

  private static String normalizeFolderName(String folder) {
    return folder.endsWith(File.separator) ? folder : folder + File.separator;
  }

  private static RollingPolicy getRollingPolicy(LoggerContext context, LoggerConfig config,
      RollingFileAppender<ILoggingEvent> appender) {
    String folder = normalizeFolderName(config.getLogFolder());
    String file = config.getLogFileName();
    FixedWindowRollingPolicy policy = new FixedWindowRollingPolicy();
    policy.setContext(context);
    policy.setFileNamePattern(folder + getBaseName(file) + "_%i." + getExtension(file));
    policy.setMinIndex(1);
    policy.setMaxIndex(25);
    policy.setParent(appender);
    policy.start();
    return policy;
  }

  private static MaskedPatternLayout getLayout(String pattern, Context context) {
    MaskedPatternLayout layout = new MaskedPatternLayout();
    layout.setPattern(pattern);
    layout.setContext(context);
    layout.start();
    return layout;
  }

  private static LoggerConfig getDefault() {
    LoggerConfig lConfig = new LoggerConfig();
    lConfig.setLoggerName("log_run");
    lConfig.setConsolePattern("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");
    lConfig.setFilePattern("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");
    return lConfig;
  }

  static Logger newInstance(Class<? extends ILoggerConfig> clazz) {
    Logger logger = loggerMap.get(clazz.getName().toLowerCase());
    if (logger != null) {
      return logger;
    }

    synchronized (myLock) {
      try {
        Object object = clazz.newInstance();
        ILoggerConfig config = (ILoggerConfig) object;
        LoggerConfig lConfig = config.newConfig();
        if (lConfig == null) {
          lConfig = config.newConfig(clazz);
        }
        logger = LoggerFactory.newLogger(lConfig);
        loggerMap.put(clazz.getName().toLowerCase(), logger);
        return logger;
      } catch (InstantiationException | IllegalAccessException e) {
        throw new IllegalStateException(e);
      }
    }
  }

  private static class MaskedPatternLayout extends PatternLayout {

    static final Pattern PATTERNCARD = Pattern.compile("\\b([0-9]{4})[0-9]{0,9}([0-9]{4})\\b");

    @Override
    public String doLayout(ILoggingEvent event) {
      String message = super.doLayout(event);
      Matcher matcher = PATTERNCARD.matcher(event.getMessage());
      if (matcher.find()) {
        message = matcher.replaceAll("$1********$2");
      }
      return message;
    }
  }

  static class LogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
      Level logLevel = event.getLevel();
      FilterReply filterValue = FilterReply.DENY;
      switch (logLevel.levelInt) {
        case Level.INFO_INT:
          filterValue = FilterReply.ACCEPT;
          break;
        case Level.DEBUG_INT:
          filterValue = FilterReply.DENY;
          break;
        case Level.ERROR_INT:
          filterValue = FilterReply.ACCEPT;
          break;
        default:
          break;
      }
      return filterValue;
    }
  }
}
