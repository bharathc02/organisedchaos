package com.everwhimsical.organisedchaos.model;

import com.everwhimsical.organisedchaos.utility.Commons;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Execution {

  private About about;
  private String id;
  private String name;
  private ZonedDateTime startDateTime;
  private ZonedDateTime endDateTime;
  private String duration;
  private Status statusEnum;
  private String status;
  private List<TestSuite> testSuites;
  private String configId;
  private List<TestMethod> testMethodList;
  private Map<String, List<TestMethod>> bugListMap;

  public Execution() {
    this.testSuites = new LinkedList<>();
    this.statusEnum = Status.PASSED;
    this.status = Status.PASSED.getDisplayValue();
  }

  public About getAbout() {
    return about;
  }

  public void setAbout(About about) {
    this.about = about;
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

  public void setStatus(String status) {
    this.status = status;
  }

  public void setStatus(Status status) {
    this.statusEnum = status;
    this.status = status.getDisplayValue();
  }

  public List<TestSuite> getTestSuites() {
    if (testSuites == null) {
      testSuites = new ArrayList<>();
    }
    return testSuites;
  }

  public void setTestSuites(List<TestSuite> testSuites) {
    this.testSuites = testSuites;
  }

  public String getConfigId() {
    return configId;
  }

  public void setConfigId(String configId) {
    this.configId = configId;
  }

  public List<TestMethod> getTestMethodList() {
    if (testMethodList == null) {
      testMethodList = new ArrayList<>();
    }
    return testMethodList;
  }

  public void setTestMethodList(List<TestMethod> testMethodList) {
    this.testMethodList = testMethodList;
  }

  public Map<String, List<TestMethod>> getBugListMap() {
    if (bugListMap == null) {
      bugListMap = new HashMap<>();
    }
    return bugListMap;
  }

  public void setBugListMap(Map<String, List<TestMethod>> bugListMap) {
    this.bugListMap = bugListMap;
  }

  public void updateBugHolder(Bug bug, TestMethod testMethod) {
    if (!getBugListMap().containsKey(bug.getMessage())) {
      getBugListMap().put(bug.getMessage(), new LinkedList<>());
    }
    getBugListMap().get(bug.getMessage()).add(testMethod);
  }

  public void startExecution() {
    this.startDateTime = ZonedDateTime.now(ZoneOffset.UTC);
  }

  public void endExecution() {
    this.endDateTime = ZonedDateTime.now(ZoneOffset.UTC);
    this.duration = Commons.calculateDuration(startDateTime, endDateTime);
  }

  public void addTestSuite(TestSuite testSuite) {
    this.testSuites.add(testSuite);
  }

  @Override
  public String toString() {
    return "Execution{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", startDateTime=" + startDateTime +
        ", duration='" + duration + '\'' +
        ", status=" + status +
        ", testSuites=" + testSuites +
        '}';
  }
}
