package com.flutterwave.middleware.notification.configurations;

import com.logviewer.springboot.LogViewerSpringBootConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@Import({LogViewerSpringBootConfig.class})
@Configuration
public class LogViewer {
}
