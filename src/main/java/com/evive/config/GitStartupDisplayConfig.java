package com.evive.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@PropertySource(value = "classpath:git.properties", ignoreResourceNotFound = true)
@Component
@Lazy(false)
public class GitStartupDisplayConfig implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GitStartupDisplayConfig.class);

    @Autowired
    private CommitProperties commitProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application was built by using the Git commit: {}", commitProperties);
    }
}
