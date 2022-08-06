package com.flutterwave.middleware.notification.configurations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adewaleijalana
 * @email adewale.ijalana@flutterwavego.com
 * @date 06/04/2022
 * @time 10:13 AM
 **/
@Data
@Component
@ConfigurationProperties(prefix = "origins")
public class MiddlewareConfig {

    @Value("${webclient.connectionTimeOut:300000}")
    private Integer connectionTimeOut;

    @Value("${webclient.connectionTimeOut:300000}")
    private Integer readTimeout;

    @Value("${webclient.writeTimeout:300000}")
    private Integer writeTimeout;

    @Value("${webclient.responseTimeout:300000}")
    private Integer responseTimeout;

    private List<String> crossOrigin = new ArrayList<>();
}
