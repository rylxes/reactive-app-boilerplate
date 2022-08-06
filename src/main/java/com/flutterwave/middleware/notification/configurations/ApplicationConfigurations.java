package com.flutterwave.middleware.notification.configurations;


import com.flutterwave.middleware.notification.domain.AppConfig;
import com.flutterwave.middleware.notification.repository.AppConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@Component("applicationConfigurations")
@DependsOn({"flywayBean"})
public class ApplicationConfigurations implements EnvironmentAware {


    private Map<String, Object> propertySource = new HashMap<>();
    private final AppConfigRepository appConfigRepo;

    @Autowired
    public ApplicationConfigurations(AppConfigRepository appConfigRepo) {
        this.appConfigRepo = appConfigRepo;
    }




    @Override
    public void setEnvironment(Environment environment) {
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        String className = this.getClass().getSimpleName();

       // Long count = appConfigRepo.count().block();

        // Map<String, Object> propertySource = new HashMap<>();
        Map<String, Object> propertySource = appConfigRepo.findAll()
                .map(this::buildPropertySource)
                .log("Class --> " + className + " Method --> " + methodName)
                .blockLast();

        // if propertySource is empty, then create a new one
        if (propertySource == null) {
            propertySource = new HashMap<>();
        }

        configurableEnvironment.getPropertySources().addAfter("systemEnvironment", new MapPropertySource("app-config", propertySource));
    }

    public Map<String, Object> buildPropertySource(AppConfig config) {
        log.info("Building property source for key: {} and value: {}", config.getKey(), config.getValue());
        propertySource.put(config.getKey(), config.getValue());
        return propertySource;
    }

}
