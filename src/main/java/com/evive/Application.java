package com.evive;

import com.evive.config.PropertiesInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = {RedisRepositoriesAutoConfiguration.class,
    RedisAutoConfiguration.class,
    RedisReactiveAutoConfiguration.class})
@ComponentScan(value = "com.evive", lazyInit = true,
               excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                                                      pattern = {"com.evive.*.dao.*",
                                                          "com.evive.*.accessors.*"}))
public class Application {
  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class)
        .initializers(new PropertiesInitializer())
        .run(args);
  }
}
