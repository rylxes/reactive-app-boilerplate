package com.flutterwave.middleware.notification.configurations;



import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.stereotype.Component;


@Component("R2dbcConfig")
@EnableR2dbcRepositories
@Configuration
public class R2dbcConfig {
}
