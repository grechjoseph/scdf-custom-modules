package com.jg.scdfcustomsource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "my-properties")
public class MyProperties {

    /**
     * Initial Value to add to the message's value.
     */
    private String initialValue;

}
