package dev.bharathc.core.utility;

import dev.bharathc.core.ChaosException;
import dev.bharathc.core.logger.SimpleLogger;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Collectors;
import org.slf4j.Logger;

/**
 * Reader assists in reading file contents (Text, Properties, etc.)
 */
public class Reader {

  private static Logger LOGGER = SimpleLogger.getLogger();

  /**
   * Read the content of file as String.
   *
   * @param filePath Resources file path
   * @return file contents
   */
  public static String text(String filePath) {
    try {
      URL url = Resources.getResource(filePath);
      return Files.readAllLines(Paths.get(url.toURI()))
          .stream().collect(Collectors.joining());
    } catch (IOException | URISyntaxException e) {
      LOGGER.error("Unable to read file ({})", filePath, e);
      throw new ChaosException(e, "Unable to read file (%s)", filePath);
    }
  }

  /**
   * Read the content of file as Properties.
   *
   * @param filePath Resources file path
   * @return {@link Properties} object
   */
  public static Properties properties(String filePath) {
    try {
      URL url = Resources.getResource(filePath);
      Properties properties = new Properties();
      properties.load(url.openStream());
      return properties;
    } catch (IOException e) {
      LOGGER.error("Unable to read properties file ({})", filePath, e);
      throw new ChaosException(e, "Unable to read properties file (%s)", filePath);
    }
  }
}