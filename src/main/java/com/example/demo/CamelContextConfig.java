package com.example.demo;

import lombok.Data;
import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.log")
public class CamelContextConfig implements CamelContextConfiguration {

    private List<String> mdcHeaderNames;

    @Override
    public void beforeApplicationStart(CamelContext camelContext) {
        if(camelContext.isUseMDCLogging()) {
            ExtendedCamelContext extendedCamelContext = camelContext.adapt(ExtendedCamelContext.class);
            extendedCamelContext.setUnitOfWorkFactory(new DemoUnitOfWorkFactory(mdcHeaderNames));
        }
    }

    @Override
    public void afterApplicationStart(CamelContext camelContext) {
    }
}
