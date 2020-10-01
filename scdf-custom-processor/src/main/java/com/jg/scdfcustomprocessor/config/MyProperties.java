package com.jg.scdfcustomprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "my-properties")
public class MyProperties {

    /**
     * Append Value to append to the message's already-present value.
     */
    private String appendValue;

}
