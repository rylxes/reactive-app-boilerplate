package com.flutterwave.middleware.notification.configurations;


import com.flutterwave.middleware.notification.coreresponse.CoreAuthErrorResponse;
import com.flutterwave.middleware.notification.coreresponse.CoreValidationErrorResponse;
import com.flutterwave.middleware.notification.exception.AuthenticationException;
import com.flutterwave.middleware.notification.exception.ModelNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author adewaleijalana
 * @email adewale.ijalana@flutterwavego.com
 * @date 06/04/2022
 * @time 12:20 PM
 **/

@Slf4j
@Configuration
public class WebClientConfig implements WebFluxConfigurer{

    @Bean
    public WebClient getWebClient(MiddlewareConfig mMiddlewareConfig){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, mMiddlewareConfig.getConnectionTimeOut())
                .responseTimeout(Duration.ofMillis(mMiddlewareConfig.getResponseTimeout()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(mMiddlewareConfig.getReadTimeout(),
                                TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(mMiddlewareConfig.getWriteTimeout(),
                                        TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .filter(errorHandlingFilter())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public static ExchangeFilterFunction errorHandlingFilter() {
        final Gson gson =
                new GsonBuilder().setPrettyPrinting().create();

        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if(clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)){
                return clientResponse.bodyToMono(CoreValidationErrorResponse.class)
                        .flatMap(errorBody -> {
                            log.info("Response body: \n{}", gson.toJson(errorBody));
                            return Mono.error(new ModelNotFoundException(errorBody.getData()));
                        });
            }else if (clientResponse.statusCode().is5xxServerError()){
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            log.info("Response body: \n{}", gson.toJson(errorBody));
                            return Mono.error(new Exception(errorBody));
                        });
            }else if (clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)){
                return clientResponse.bodyToMono(CoreAuthErrorResponse.class)
                        .flatMap(errorBody -> {
                            log.info("Response body: \n{}", gson.toJson(errorBody));
                            return Mono.error(new AuthenticationException(errorBody.getData().getResponsemessage()));
                        });
            } else {
                return Mono.just(clientResponse);
            }
        });
    }

}
