package com.everwhimsical.organisedchaos.model;

import com.everwhimsical.organisedchaos.utility.Commons;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestMethod {

    private String id;
    private String testMethodId;
    private String name;
    private String description;
    private boolean configuration;
    private String methodType;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String duration;
    private Status statusEnum;
    private String status;
    private Bug bug;
    private String testClass;
    private String test;
    private Object[] parameters;
    private String suite;
    private List<String> logOutput;
    private String userAgent;
    private List<FileMetaData> resourceMetaDataList;
    private List<UserInterface> userInterfaces;
    private List<Webservice> webservices;

    public TestMethod() {
        this.logOutput = new LinkedList<>();
        this.userInterfaces = new LinkedList<>();
        this.webservices = new LinkedList<>();
        this.resourceMetaDataList = new LinkedList<>();
        this.statusEnum = Status.PASSED;
        this.status = Status.PASSED.getDisplayValue();
        this.configuration = false;
        this.methodType = "TestMethod";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfiguration() {
        return configuration;
    }

    public void setConfiguration(boolean configuration) {
        this.configuration = configuration;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
        this.duration = Commons.calculateDuration(startDateTime, endDateTime);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Status getStatusEnum() {
        return statusEnum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.statusEnum = status;
        this.status = status.getDisplayValue();
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public List<String> getLogOutput() {
        if (logOutput == null) {
            logOutput = new ArrayList<>();
        }
        return logOutput;
    }

    public void setLogOutput(List<String> logOutput) {
        this.logOutput = logOutput;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public List<FileMetaData> getResourceMetaDataList() {
        if (resourceMetaDataList == null) {
            resourceMetaDataList = new ArrayList<>();
        }
        return resourceMetaDataList;
    }

    public void setResourceMetaDataList(
        List<FileMetaData> resourceMetaDataList) {
        this.resourceMetaDataList = resourceMetaDataList;
    }

    public String getTestMethodId() {
        return testMethodId;
    }

    public void setTestMethodId(String testMethodId) {
        this.testMethodId = testMethodId;
    }

    public void generateTestMethodId(String testMethodName) {
        this.testMethodId = String
            .format("%s_%s_%s", testMethodName, System.currentTimeMillis(),
                Thread.currentThread().getId());
    }

    public List<UserInterface> getUserInterfaces() {
        return userInterfaces;
    }

    public void setUserInterfaces(
        List<UserInterface> userInterfaces) {
        this.userInterfaces = userInterfaces;
    }

    public List<Webservice> getWebservices() {
        return webservices;
    }

    public void setWebservices(List<Webservice> webservices) {
        this.webservices = webservices;
    }

    public void addResourceMetaData(FileMetaData resourceMetaData) {
        this.resourceMetaDataList.add(resourceMetaData);
    }

    public void addUserInterface(UserInterface userInterface) {
        this.userInterfaces.add(userInterface);
    }

    public void addWebservice(Webservice webservice) {
        this.webservices.add(webservice);
    }

    @Override
    public String toString() {
        return "TestMethod{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", startDateTime=" + startDateTime +
            ", duration='" + duration + '\'' +
            ", status=" + status +
            ", bug=" + bug +
            '}';
    }
}
