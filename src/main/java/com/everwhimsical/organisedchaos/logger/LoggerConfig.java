package com.everwhimsical.organisedchaos.logger;

class LoggerConfig {

    private String logFolder;
    private String logLevel;
    private String loggerName;
    private String[] ignoreList = new String[]{};
    private String logFileName;
    private String fileSize = "5MB";
    private String consolePattern;
    private String filePattern;

    /**
     * @return - The log file name.
     */
    String getLogFileName() {
        return logFileName;
    }

    /**
     * @param logFileName - The log file name.
     */
    void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    /**
     * @return - Max size after which the log file should be rolled. E.g., can be <code>5MB</code>
     * (or) <code>100KB</code> etc.,
     */
    String getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize - Max size after which the log file should be rolled. E.g., can be
     * <code>5MB</code> (or) <code>100KB</code> etc.,
     */
    void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return - The location where the logs are to be stored.
     */
    String getLogFolder() {
        return logFolder;
    }

    /**
     * @param logFolder - The location where the logs are to be stored.
     */
    void setLogFolder(String logFolder) {
        this.logFolder = logFolder;
    }

    /**
     * @return - A String that indicates the log level for e.g., <code>WARN INFO</code>
     */
    String getLogLevel() {
        return logLevel;
    }

    /**
     * @param logLevel - A String that indicates the log level for e.g., <code>WARN INFO</code>
     */
    void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * @return - The name of the logger.
     */
    String getLoggerName() {
        return loggerName;
    }

    /**
     * @param loggerName - The name of the logger.
     */
    void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    /**
     * @return - List of logger names that are to be silenced from the log file and also from the
     * console logs.
     */
    String[] getIgnoreList() {
        return ignoreList;
    }

    /**
     * @param ignoreList - List of logger names that are to be silenced from the log file and also
     * from the console logs.
     */
    void setIgnoreList(String... ignoreList) {
        this.ignoreList = ignoreList;
    }

    /**
     * Get the console pattern to be used by Logger
     *
     * @return pattern value
     */
    String getConsolePattern() {
        return consolePattern;
    }

    /**
     * Set the console pattern to be used by Logger
     *
     * @param consolePattern pattern value
     */
    void setConsolePattern(String consolePattern) {
        this.consolePattern = consolePattern;
    }

    /**
     * Get the file pattern to be used by Logger
     *
     * @return pattern value
     */
    String getFilePattern() {
        return filePattern;
    }

    /**
     * Set the console pattern to be used by Logger
     *
     * @param filePattern pattern value
     */
    void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    /**
     * Helps validate the mandatory parameters which are absolutely necessary for the configuration
     * to be treated as valid and be used.
     */
    final void validateInternals() {
//        checkState(Commons.isNotNullAndNotEmpty(logFolder), "Log file name cannot be null or empty.");
//        checkState(Commons.isNotNullAndNotEmpty(logLevel), "Log Level cannot be null or empty.");
//        checkState(Commons.isNotNullAndNotEmpty(loggerName), "Logger name cannot be null or empty.");
    }
}
