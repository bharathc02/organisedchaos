package com.everwhimsical.organisedchaos;


import com.everwhimsical.organisedchaos.utility.Commons;

public class ChaosException extends RuntimeException {

  public ChaosException(String message) {
    super(message);
  }

  public ChaosException(String message, String... messageArray) {
    super(Commons.buildVarArgs(message, messageArray));
  }

  public ChaosException(String message, Throwable cause) {
    super(message, cause);
  }

  public ChaosException(String message, Throwable cause, String... messageArray) {
    super(Commons.buildVarArgs(message, messageArray), cause);
  }
}
