package com.evive.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author evivehealth on 5/6/17.
 */
@Slf4j
@Component
public class JsonUtil {
  private ObjectMapper objectMapper;

  @Autowired
  public JsonUtil(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    objectMapper.registerModule(javaTimeModule);
    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
  }

  public <T> T mapJsonToObject(String jsonString, Class<T> tClass) {
    try {
      return objectMapper.readValue(jsonString, tClass);
    } catch (Exception e) {
      log.error("Failed Json To Object mapping. {}", e.getMessage());
      return null;
    }
  }

  public <T> T mapJsonToModel(String content, TypeReference<T> typeReference) {
    try {
      return objectMapper.readValue(content, typeReference);
    } catch (IOException e) {
      log.error("Error mapping content JSON: ", e);
      return null;
    }
  }

  public String mapObjectToJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      log.error("Failed Object to Json mapping. {}", e.getMessage());
      return "";
    }
  }

  public Map<String, String> getJsonToKeyValueMap(String jsonString) {
    try {
      return objectMapper.readValue(jsonString, Map.class);
    } catch (Exception e) {
      log.error("Failed Json To Object mapping. {}", e.getMessage());
      return null;
    }
  }

  public Set<String> getJsonToSet(String jsonString) {
    try {
      return objectMapper.readValue(jsonString, Set.class);
    } catch (Exception e) {
      log.error("Failed Json To Object mapping. {}", e.getMessage());
      return null;
    }
  }

  public List<String> getJsonToList(String jsonString) {
    try {
      return objectMapper.readValue(jsonString, List.class);
    } catch (Exception e) {
      log.error("Failed Json To Object mapping. {}", e.getMessage());
      return null;
    }
  }

  public <T> List<T> getJsonToList(String jsonString, Class<T> object) {
    try {
      return objectMapper.readValue(jsonString, List.class);
    } catch (Exception e) {
      log.error("Failed Json To Object mapping. {}", e.getMessage());
      return null;
    }
  }

  public Map<String, String> mapFileToJson(File file) {
    try {
      String json = objectMapper.readTree(file).toString();
      return getJsonToKeyValueMap(json);
    } catch (IOException e) {
      log.error("Failed File To Json mapping. {}", e.getMessage());
      e.printStackTrace();
      return Collections.emptyMap();
    }
  }

  public <T> T mapFileToModel(File file, Class<T> object) {
    try {
      return objectMapper.readValue(file, object);
    } catch (IOException e) {
      log.error("Failed File To Json mapping. {}", e.getMessage());
      e.printStackTrace();
      return null;
    }
  }
}

