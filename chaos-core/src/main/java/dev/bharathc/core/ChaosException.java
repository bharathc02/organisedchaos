package dev.bharathc.core;


public class ChaosException extends RuntimeException {

  public ChaosException(String message, Object... messageArray) {
    super(consumeMessage(message, messageArray));
  }

  public ChaosException(Throwable cause, String message, Object... messageArray) {
    super(consumeMessage(message, messageArray), cause);
  }

  public static String consumeMessage(String message, Object... messageArray) {
    return String.format(message, messageArray);
  }

}
