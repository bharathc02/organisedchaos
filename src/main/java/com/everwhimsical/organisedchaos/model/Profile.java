package com.everwhimsical.organisedchaos.model;

public class Profile {

  private String profileName;

  private Profile(String profileName) {
    this.profileName = profileName;
  }

  public static Profile forName(String profileName) {
    return new Profile(profileName);
  }

  public String getProfileName() {
    return profileName;
  }

  @Override
  public String toString() {
    return "Profile{" +
        "profileName='" + profileName + '\'' +
        '}';
  }
}
