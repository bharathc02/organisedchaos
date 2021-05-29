package com.everwhimsical.organisedchaos.utility;

import com.everwhimsical.organisedchaos.ChaosException;
import com.everwhimsical.organisedchaos.logger.SimpleLogger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public final class Commons {

  private static final AtomicLong LAST_TIME_MS = new AtomicLong();
  private static Logger LOGGER = SimpleLogger.getLogger();

  private Commons() {

  }

  public static String inputStreamToString(InputStream inputStream) {
    try {
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean isNull(Object obj) {
    return (obj == null);
  }

  public static boolean isNotNullAndNotEmpty(String obj) {
    return obj != null && !obj.isEmpty();
  }

  public static boolean isNullOrEmpty(String obj) {
    return obj == null || obj.isEmpty();
  }

  public static <T> T checkNotNull(T obj, String errorMessageTemplate) {
    if (obj == null) {
      throw new NullPointerException(errorMessageTemplate);
    } else {
      return obj;
    }
  }

  public static <T> List<T> checkNotNullAndEmpty(List<T> obj, String objectDescription) {
    if (obj == null || obj.isEmpty()) {
      throw new ChaosException(objectDescription + " cannot be Null or Empty");
    }
    return obj;
  }

  public static String checkNotNullAndEmpty(String obj, String objectDescription) {
    if (obj == null || obj.isEmpty()) {
      throw new ChaosException(objectDescription + " cannot be Null or Empty");
    }
    return obj;
  }

  public static void sleep(int duration, TimeUnit unit) {
    try {
      Thread.sleep(unit.toMillis(duration));
    } catch (InterruptedException ignored) {

    }
  }

  /**
   * Get unique timestamp.
   *
   * @return Time in long format
   */
  public static long getUniqueCurrentTime() {
    long now = System.currentTimeMillis();
    while (true) {
      long lastTime = LAST_TIME_MS.get();
      if (lastTime >= now) {
        now = lastTime + 1;
      }
      if (LAST_TIME_MS.compareAndSet(lastTime, now)) {
        return now;
      }
    }
  }

  /**
   * Build a String replacing placeholders with varargs values.
   *
   * @param data    Data to be transformed
   * @param varargs Array of Objects to be replaced.
   * @return Transformed String
   */
  public static String buildVarArgs(String data, Object... varargs) {
    String output = data;
    checkNotNullAndEmpty(output, "Data cannot be NULL or empty for VarArgs");
    for (Object argObj : varargs) {
      String arg = String.valueOf(argObj);
      arg = Optional.ofNullable(arg).orElse("");
      if (!output.contains("{}")) {
        LOGGER.error("Invalid/extra var arg param passed. "
            + "Current data: {}, Var arg: {}", output, arg);
        continue;
      }
      output = output.replaceFirst("\\{}", arg);
    }
    if (output.contains("{}")) {
      LOGGER.error("Invalid/less var arg param passed. Current data: {}, Var arg: {}",
          output, varargs);
    }
    return output;
  }

  /**
   * Calculates the time duration between given two {@link ZonedDateTime}s
   *
   * @param startTime, Start time in the {@link ZonedDateTime} format
   * @param endTime,   End time in the {@link ZonedDateTime} format
   * @return The duration string in the format of hh:mm:ss
   */
  public static String calculateDuration(ZonedDateTime startTime, ZonedDateTime endTime) {
    Duration duration = Duration.between(startTime, endTime);
    long durationInSeconds = duration.getSeconds();

    return String
        .format("%02dh:%02dm:%02ds", durationInSeconds / 3600, (durationInSeconds % 3600) / 60,
            (durationInSeconds % 60));
  }

  public static boolean isValidURL(String url) {
    try {
      new URL(url).toURI();
      return true;
    } catch (Exception ignored) {
      return false;
    }
  }

  /**
   * Capture resource from the current test result context.
   *
   * @param resource     resource bytes
   * @param resourcePath Path object
   * @return optional file path at which the captured resource is stored
   */
  public static Optional<File> storeResource(byte[] resource, Path resourcePath) {
    if ((resource == null) || (resource.length == 0)) {
      return Optional.empty();
    }
    try {
      Files.write(resourcePath, resource);
    } catch (IOException e) {
      LOGGER.warn("I/O error saving to ({}); no resource was stored", resourcePath, e);
      return Optional.empty();
    }
    return Optional.of(resourcePath.toFile());
  }

  /**
   * Encode the String with base64
   *
   * @param encStr String to be encoded
   * @return encoded string
   */
  public static String encodeBase64(String encStr) {
    byte[] bytesEncoded = Base64.getEncoder().encode(encStr.getBytes());
    return new String(bytesEncoded);
  }

  /**
   * Decode the String with base64
   *
   * @param decStr String to be decoded
   * @return decoded string
   */
  public static String decodeBase64(String decStr) {
    byte[] bytesDecoded = Base64.getDecoder().decode(decStr.getBytes());
    return new String(bytesDecoded);
  }

  /**
   * Combine multiple predicates into one.
   *
   * @param predicates Predicate varargs
   * @param <T>        Type
   * @return Predicate
   */
  public static <T> Predicate<T> andPredicates(Predicate<T>... predicates) {
    return Arrays.stream(predicates).reduce(Predicate::and).orElse(x -> true);
  }
}
