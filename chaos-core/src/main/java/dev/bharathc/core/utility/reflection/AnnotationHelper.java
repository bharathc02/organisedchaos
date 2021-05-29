package dev.bharathc.core.utility.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 */
public class AnnotationHelper {

  private static final String ANNOTATION_DATA = "annotationData";
  private static final String ANNOTATIONS = "annotations";

  private AnnotationHelper() {

  }

  public static void alterAnnotation(Class clazzObject,
      Class<? extends Annotation> originalAnnotation,
      Annotation alteredAnnotation) {
    validateArgument(clazzObject, "Class Name");
    validateArgument(originalAnnotation, "Original annotation");
    validateArgument(alteredAnnotation, "New value for annotation");
    Map<Class<? extends Annotation>, Annotation> annotationMap = retrieveMap(clazzObject);
    annotationMap.put(originalAnnotation, alteredAnnotation);
  }

  private static void validateArgument(Object object, String prefix) {
    if (object == null) {
      throw new IllegalArgumentException(prefix + " cannot be null.");
    }
  }

  @SuppressWarnings("unchecked")
  private static Map<Class<? extends Annotation>, Annotation> retrieveMap(Class clazzObject) {
    try {
      Method method = Class.class.getDeclaredMethod(ANNOTATION_DATA, null);
      method.setAccessible(true);
      Object object = method.invoke(clazzObject);
      Field annotations = clazzObject.getDeclaredField(ANNOTATIONS);
      annotations.setAccessible(true);
      return (Map<Class<? extends Annotation>, Annotation>) annotations.get(object);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
