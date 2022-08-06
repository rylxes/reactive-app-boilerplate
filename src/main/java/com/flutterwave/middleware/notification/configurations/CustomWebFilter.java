package com.flutterwave.middleware.notification.configurations;


import com.flutterwave.middleware.notification.reactive.BodyCaptureExchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class CustomWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        BodyCaptureExchange bodyCaptureExchange = new BodyCaptureExchange(serverWebExchange);
        return webFilterChain.filter(bodyCaptureExchange).doOnSuccess((se) -> {
            log.info("Body request " + bodyCaptureExchange.getRequest().getFullBody());
            log.info("Body response " + bodyCaptureExchange.getResponse().getFullBody());
        });
    }

}
