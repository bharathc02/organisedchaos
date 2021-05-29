package com.everwhimsical.organisedchaos.model;

import java.io.File;
import java.io.FileWriter;

/**
 * ContentHolder helps in performing operations on content <br/> Currently supported content type is
 * JSON.
 *
 * @param <T> Generic return type of content object.
 */
public interface ContentHolder<T> {

  /**
   * Get the content as JSON String
   *
   * @return content
   */
  String asJson();

  /**
   * Get the content as JSON String
   *
   * @return content
   */
  String asXml();

  /**
   * Get the content as specified class object
   *
   * @return content POJO
   */
  T asObject();

  /**
   * Get the content as File object
   *
   * @return File object
   */
  File asFile();

  /**
   * Get the content as File object
   *
   * @param fileWriter file writer object
   */
  void asFile(FileWriter fileWriter);
}
