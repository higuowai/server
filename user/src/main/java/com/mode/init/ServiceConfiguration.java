package com.mode.init;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Java annotation based configuration for service beans (via @ComponentScan) and properties.
 */
@Configuration
@ComponentScan(basePackages = {"com.mode.service", "com.mode.security"})
@PropertySource(value = {"classpath:jdbc.properties"})
public class ServiceConfiguration {

    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}