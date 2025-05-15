package com.api.test.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The {@code LoadDataFromJson} class is responsible for loading and parsing data from JSON files
 * into Java objects. This class provides methods to read JSON data and convert it into relevant
 * data structures for use in test cases or application logic.
 *
 * <p>It simplifies the process of reading data from JSON files, deserializing the data, and
 * returning it in a usable format, such as a list of objects or a specific data structure.
 */
public class LoadDataFromJson {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public <T> List<T> loadObjects(String resourceName, Class<T> clazz) {
    InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
    try {
      return objectMapper.readValue(
          is, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
