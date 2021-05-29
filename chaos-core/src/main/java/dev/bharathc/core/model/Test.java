package dev.bharathc.core.model;

import dev.bharathc.core.utility.Commons;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test {

  private String id;
  private String name;
  private ZonedDateTime startDateTime;
  private ZonedDateTime endDateTime;
  private String duration;
  private Status statusEnum;
  private String status;
  private List<TestMethod> testMethods;

  public Test() {
    this.testMethods = new LinkedList<>();
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

  public String getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.statusEnum = status;
    this.status = status.getDisplayValue();
  }

  public Status getStatusEnum() {
    return statusEnum;
  }

  public List<TestMethod> getTestMethods() {
    if (testMethods == null) {
      testMethods = new ArrayList<>();
    }
    return testMethods;
  }

  public void setTestMethods(List<TestMethod> testMethods) {
    this.testMethods = testMethods;
  }

  public void addTestMethod(TestMethod testMethod) {
    this.testMethods.add(testMethod);
  }

  public void updateTestStart() {
    setStartDateTime(ZonedDateTime.now(ZoneOffset.UTC));
  }

  public void updateTestEnd() {
    setEndDateTime(ZonedDateTime.now(ZoneOffset.UTC));
  }

  @Override
  public String toString() {
    return "Test{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", startDateTime=" + startDateTime +
        ", duration='" + duration + '\'' +
        ", status=" + status +
        ", testMethods=" + testMethods +
        '}';
  }
}
