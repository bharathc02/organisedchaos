package com.everwhimsical.organisedchaos.model;

public enum Status {
  FAILED("FAILED", 1),
  SKIPPED("SKIPPED", 2),
  INCOMPLETE("INCOMPLETE", 3),
  PASSED("PASSED", 4);

  private int priority;
  private String displayValue;

  Status(String displayValue, int priority) {
    this.displayValue = displayValue;
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
