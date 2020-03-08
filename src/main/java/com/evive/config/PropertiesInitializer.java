package com.evive.config;

//import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.ResourcePropertySource;

//import java.util.List;


@Slf4j
@Configuration
public class PropertiesInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

//  private static final List<String> externalPropFiles = ImmutableList.of("url.properties");
//
//  private static final List<String> classpathPropFiles = ImmutableList.of();
//
//  private static final List<String> externalYamlFiles = ImmutableList.of();
//
//  private static final YamlPropertySourceLoader YAML_PROPERTY_SOURCE_LOADER = new YamlPropertySourceLoader();

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
//    final ConfigurableEnvironment environment = applicationContext.getEnvironment();
//    String propertyFilesPath = environment.getProperty("props.file.path");
//    final MutablePropertySources mutablePropertySources = environment.getPropertySources();
//    for (String propFile : classpathPropFiles) {
//      try {
//        mutablePropertySources.addLast(new ResourcePropertySource(propFile));
//      } catch (Exception e) {
//        log.error("Error reading property files {}", propFile, e);
//      }
//    }
//    for (String propFile : externalPropFiles) {
//      try {
//        mutablePropertySources
//            .addLast(new ResourcePropertySource("file:" + propertyFilesPath + "/" + propFile));
//      } catch (Exception e) {
//        log.error("Error reading property files from {}", propertyFilesPath, e);
//      }
//    }
//    for (String propFile : externalYamlFiles) {
//      try {
//        Resource resource = new FileSystemResource(
//            format("{0}/{1}", propertyFilesPath, propFile));
//        YAML_PROPERTY_SOURCE_LOADER.load(propFile, resource)
//            .forEach(mutablePropertySources::addLast);
//      } catch (Exception e) {
//        log.error("Error reading property files from {}", propertyFilesPath, e);
//      }
//    }
  }
}
