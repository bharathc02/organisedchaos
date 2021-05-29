package com.everwhimsical.organisedchaos.model;

import com.everwhimsical.organisedchaos.utility.Commons;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class TestSuite {

  private String id;
  private String name;
  private ZonedDateTime startDateTime;
  private ZonedDateTime endDateTime;
  private String duration;
  private Status statusEnum;
  private String status;
  private List<Test> tests;

  public TestSuite() {
    this.tests = new LinkedList<>();
    this.statusEnum = Status.PASSED;
    this.status = Status.PASSED.getDisplayValue();
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
    this.duration = Commons.calculateDuration(startDateTime, endDateTime);
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

  public List<Test> getTests() {
    return tests;
  }

  public void setTests(List<Test> tests) {
    this.tests = tests;
  }

  public void addTest(Test test) {
    this.tests.add(test);
  }

  public void updateTestSuiteStart() {
    setStartDateTime(ZonedDateTime.now(ZoneOffset.UTC));
  }

  public void updateTestSuiteEnd() {
    setEndDateTime(ZonedDateTime.now(ZoneOffset.UTC));
  }

  @Override
  public String toString() {
    return "TestSuite{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", startDateTime=" + startDateTime +
        ", duration='" + duration + '\'' +
        ", status=" + status +
        ", tests=" + tests +
        '}';
  }
}
