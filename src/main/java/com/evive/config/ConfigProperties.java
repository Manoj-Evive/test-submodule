package com.evive.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author evivehealth on 10/10/16.
 */
@Data
@Component
public class ConfigProperties {
    @Value("${history.url}")
    private String historyUrl;

    @Value("${resetApiUrl}")
    private String resetApiUrl;
}
